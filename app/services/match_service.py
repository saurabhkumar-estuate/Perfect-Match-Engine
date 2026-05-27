import logging
from typing import List
from sqlalchemy.ext.asyncio import AsyncSession
from sqlalchemy.future import select
from app.models.profile import Profile
from app.schemas.match import MatchRequest, MatchResponse

logger = logging.getLogger(__name__)

class MatchService:
    def __init__(self, db_session: AsyncSession):
        self.db = db_session

    async def find_matches(self, request: MatchRequest) -> List[MatchResponse]:
        """
        Core matching logic. Replaces the Java findMatches method.
        """
        logger.info(f"Processing match request for user: {request.user_id}")
        
        # Fetch candidate profiles (Equivalent to JPA Repository call)
        result = await self.db.execute(select(Profile).where(Profile.is_active == True))
        candidates = result.scalars().all()

        matches = []
        for candidate in candidates:
            score = self._calculate_score(request.preferences, candidate.tags)
            if score >= request.min_score_threshold:
                matches.append(MatchResponse(
                    match_id=candidate.id, # Simplified for example
                    score=score,
                    matched_profile_id=candidate.id,
                    metadata={"name": candidate.name}
                ))

        # Sort by score descending (Java Stream equivalent: .sorted(Comparator.comparing(MatchResponse::getScore).reversed()))
        matches.sort(key=lambda x: x.score, reverse=True)
        
        return matches[:request.max_results]

    def _calculate_score(self, prefs: List[str], tags: List[str]) -> float:
        """
        Internal scoring algorithm.
        """
        if not prefs:
            return 0.0
        intersection = set(prefs) & set(tags)
        return len(intersection) / len(prefs)