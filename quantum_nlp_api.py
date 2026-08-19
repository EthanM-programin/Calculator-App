from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
import httpx
import re
import random

app = FastAPI(title="Quantum NLP Calculator API")

class CalculationRequest(BaseModel):
    query: str

async def fetch_quantum_number() -> int:
    url = "https://qrng.anu.edu.au/API/jsonI.php?length=1&type=uint8"
    try:
        # Add a 5-second timeout so the app doesn't hang forever if the API is down
        async with httpx.AsyncClient(timeout=5.0) as client:
            response = await client.get(url)
            if response.status_code == 200:
                data = response.json()
                raw_qnum = data["data"][0]
                scaled_qnum = (raw_qnum % 10) + 1
                return scaled_qnum, "Quantum Integer"
            else:
                # If the API rejects us, fallback to a pseudo-random number
                return random.randint(1, 10), "Classical Fallback Integer"
    except Exception:
        # If the internet drops or the server completely times out, fallback
        return random.randint(1, 10), "Classical Fallback Integer"

@app.post("/api/calculate")
async def process_natural_language(request: CalculationRequest):
    text = request.query.lower()

    # NLP / Intent Extraction
    is_addition = "add" in text or "+" in text or "plus" in text
    is_subtraction = "subtract" in text or "minus" in text or "-" in text
    is_multiplication = "multiply" in text or "times" in text or "*" in text
    is_division = "divide" in text or "/" in text

    numbers = re.findall(r'\d+', text)
    if not numbers:
        raise HTTPException(status_code=400, detail="Could not find a number in your sentence.")

    user_number = float(numbers[0])

    # Quantum Integration
    is_quantum = "quantum" in text or "random" in text

    if is_quantum:
        operand, operand_type = await fetch_quantum_number()
    else:
        if len(numbers) > 1:
            operand = float(numbers[1])
            operand_type = "Classical Integer"
        else:
            raise HTTPException(status_code=400, detail="Provide a second number or ask for a 'quantum' number.")

    # Execution
    result = None
    operation_name = ""

    if is_addition:
        result = user_number + operand
        operation_name = "+"
    elif is_subtraction:
        result = user_number - operand
        operation_name = "-"
    elif is_multiplication:
        result = user_number * operand
        operation_name = "*"
    elif is_division:
        if operand == 0:
            raise HTTPException(status_code=400, detail="Quantum zero division error.")
        result = user_number / operand
        operation_name = "/"
    else:
        raise HTTPException(status_code=400, detail="Could not determine the mathematical operation.")

    return {
        "original_query": request.query,
        "parsed_equation": f"{user_number} {operation_name} {operand}",
        "operand_source": operand_type,
        "result": result,
        "explanation": f"Extacted {user_number}, requested a {operand_type} ({operand}), and executed."
    }