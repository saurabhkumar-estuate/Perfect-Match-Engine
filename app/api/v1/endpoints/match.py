from fastapi import APIRouter, Depends, HTTPException, status
from sqlalchemy.ext.asyncio import AsyncSession
from typing import List
from app.db.session import get_db
from app.schemas.match import MatchRequest, MatchResponse
from app.services.match_service import MatchService

router = APIRouter()

@router.post("/", response_model=List[MatchResponse], status_code=status.HTTP_200_OK)
async def get_matches(
    request: MatchRequest,
    db: AsyncSession = Depends(get_db)
):
    """
    Endpoint to trigger the matching engine.
    """
    try:
        service = MatchService(db)
        results = await service.find_matches(request)
        return results
    except Exception as e:
        # Equivalent to @ControllerAdvice error handling
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"An error occurred during matching: {str(e)}"
        )