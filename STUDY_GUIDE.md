# 📚 Project Study Guide: Libraries & Commands

This document breaks down the specific libraries, functions, and terminal commands utilized to build the Quantum-AI microservice.

## 🐍 Python Backend Libraries

### 1. `fastapi`
The core web framework used to build the API. It is designed to be exceptionally fast and easy to write.
* **`FastAPI()`**: Initializes the web application.
  * *Syntax:* `app = FastAPI(title="My API")`
* **`@app.post()`**: A "decorator" that tells FastAPI to listen for incoming HTTP POST requests at a specific URL route.
  * *Syntax:* `@app.post("/api/calculate")`
* **`HTTPException`**: Used to safely throw an error back to the Java client if something goes wrong (like a zero division), preventing a server crash.
  * *Syntax:* `raise HTTPException(status_code=400, detail="Error message")`

### 2. `pydantic`
A data validation library that FastAPI uses under the hood to ensure incoming JSON data is formatted correctly.
* **`BaseModel`**: You inherit from this to define the exact shape of the data you expect from Java.
  * *Syntax:* 
    ```python
    class CalculationRequest(BaseModel):
        query: str
    ```

### 3. `httpx`
A modern, asynchronous HTTP client for Python. It allows our backend to talk to the ANU Quantum Server without freezing the rest of our application.
* **`AsyncClient()`**: Opens a network connection.
  * *Syntax:* `async with httpx.AsyncClient(timeout=5.0) as client:`
* **`client.get()`**: Sends an HTTP GET request to the target URL.
  * *Syntax:* `response = await client.get(url)`

### 4. `re` (Regular Expressions)
A built-in Python module for string searching and manipulation. Used here as our lightweight NLP extractor to find numbers within the natural language sentence.
* **`re.findall()`**: Scans a string and returns a list of all matches based on a specific pattern. We used the pattern `r'\d+'` to find all digits.
  * *Syntax:* `numbers = re.findall(r'\d+', "Divide 100 by 2")` # Returns ['100', '2']

### 5. `random`
A built-in Python module for generating pseudo-random numbers. Used in our application as a "graceful fallback" to keep the math working if the external Quantum API is down or rate-limited.
* **`random.randint()`**: Returns a random integer between a given minimum and maximum range.
  * *Syntax:* `fallback_number = random.randint(1, 10)`

----------------------------------------------------------

## ☕ Java Frontend Libraries

### 1. `java.util.Scanner`
A built-in Java utility used to read input streams, primarily used here to capture what the user types into the terminal.
* **`Scanner()`**: Initializes the scanner to read from `System.in` (the console).
  * *Syntax:* `Scanner scnr = new Scanner(System.in);`
* **`nextLine()`**: Pauses the program and waits for the user to press Enter, capturing their entire typed sentence as a String.
  * *Syntax:* `String userInput = scnr.nextLine();`

### 2. `java.util.ArrayList`
A built-in Java data structure that acts as a resizable array. Used here to store the history of calculations without needing to know in advance how many math problems the user will solve.
* **`ArrayList<>()`**: Initializes the empty list.
  * *Syntax:* `ArrayList<String> history = new ArrayList<>();`
* **`add()`**: Appends a new calculation log to the end of the history list.
  * *Syntax:* `history.add(userInput + " -> " + resultStr);`

### 3. `java.net.URI`
A standard library used to securely parse and format web addresses (Uniform Resource Identifiers).
* **`URI.create()`**: Converts a raw string URL into a secure URI object that the HTTP client can use.
  * *Syntax:* `URI.create("http://127.0.0.1:8000/api/calculate")`

### 4. `java.time.Duration`
A built-in utility for measuring time. Used in our network requests to ensure the Java program doesn't hang forever if the Python server crashes.
* **`Duration.ofSeconds()`**: Creates a time limit configuration.
  * *Syntax:* `.timeout(Duration.ofSeconds(10))`

### 5. `java.net.http.*`
The modern Java HTTP client introduced in Java 11. It handles sending JSON data over the local network to our Python server.
* **`HttpClient`**: The main object that executes the network requests.
  * *Syntax:* `HttpClient client = HttpClient.newHttpClient();`
* **`HttpRequest.newBuilder()`**: A builder pattern used to construct the destination, headers, and payload body of the request.
  * *Syntax:* 
    ```java
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create("[http://127.0.0.1:8000/api/calculate](http://127.0.0.1:8000/api/calculate)"))
        .POST(HttpRequest.BodyPublishers.ofString(jsonString))
        .build();
    ```
* **`HttpResponse`**: Holds the incoming JSON data that the Python server sends back after finishing the math.
  * *Syntax:* `HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());`

----------------------------------------------------------

## 💻 Command Line Breakdown

| Command | Word | Meaning |
| ------- | ---- | ------- |
| | **`git clone <url>`** | **`git`** | The version control system used to track changes in code. |
| | **`clone`** | Tells Git to download an exact, working copy of a remote repository from GitHub onto your local machine. |
| | **`cd Calculator-App`** | **`cd`** | "Change Directory". This command navigates your terminal's active focus inside the newly downloaded project folder. |
| | **`pip install fastapi uvicorn...`** | **`pip`** | "Package Installer for Python". The tool used to download libraries from the internet. |
| | **`install`** | The command telling `pip` to download and set up the following packages. |
| | **`python -m uvicorn quantum_nlp_api:app --reload`** | **`python -m`** | Runs a specific Python module (in this case, Uvicorn) directly from the command line. |
| | **`uvicorn`** | The lightning-fast web server that actually hosts the FastAPI application. |
| | **`quantum_nlp_api:app`** | Tells Uvicorn where to look. It means: "Open the `quantum_nlp_api.py` file, and run the variable named `app`." |
| | **`--reload`** | A developer tool that watches your files and automatically restarts the server the second you hit `Ctrl+S`. |
| | **`javac Calculator.java`** | **`javac`** | The Java Compiler. It translates human-readable `.java` code into machine-readable `.class` byte-code. |
| | **`java Calculator`** | **`java`** | The Java Virtual Machine (JVM). It executes the compiled `.class` file to run the program. |