from pydantic import BaseModel, Field, ConfigDict
from typing import List, Optional
from uuid import UUID

class MatchRequest(BaseModel):
    """
    DTO for match requests, replacing the Java MatchRequest POJO.
    """
    model_config = ConfigDict(from_attributes=True)

    user_id: UUID = Field(..., description="The ID of the user seeking a match")
    preferences: List[str] = Field(default_factory=list, description="List of preference tags")
    max_results: int = Field(default=10, ge=1, le=100)
    min_score_threshold: float = Field(default=0.5, ge=0.0, le=1.0)

class MatchResponse(BaseModel):
    model_config = ConfigDict(from_attributes=True)

    match_id: UUID
    score: float
    matched_profile_id: UUID
    metadata: dict = Field(default_factory=dict)