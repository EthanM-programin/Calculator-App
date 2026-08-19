# 🌌 Quantum-AI Calculator Microservice

A full-stack, cross-language microservice that transforms a standard command-line calculator into an intelligent, quantum-powered engine.

Instead of rigid mathematical syntax, this application uses **Natural Language Processing (NLP)** to understand conversational math quries (e.g., *"Divide 100 by a quantum number"*). The Java frontend communicates via a REST API with a Python FastAPI backend, which extracts the mathematical intent and fetches **true random number** dervied from quantum vacuum fluctuations via the Australian National University (ANU) Quantum Server.

## ✨ Features
* **Natural Language Parsing:** Uses Python regular expressions to extract mathematical operations and numberical values from conversational strings.
* **True Quantum Randomness:** Bypasses classical pseudo-random generation (`Math.random()`) by executing asynchronous HTTP requests to measure real-time quantum field fluctuations.
* **Microservice Architecture:** Decouples the user interface (Java) from the processing logic (Python) using a modern REST API communicating via JSON payloads.
* **Graceful Degradation:** Implements a robust fallback mechanism that automatically substitutes classical psuedo-random numbers if the external quantum API experiences rate-limiting or downtime.

## 🚀 Architecture Stack
* **Frontend / CLI:** Java 21 (Modern `java.net.http` client)
* **Backend / API:** Python 3.14 (FastAPI, Uvicorn)
* **External Integration:** ANU Quantum Random Numbers API (`httpx` asynchronous client)

## ⚙️ I

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/EthanM-programin/Calculator-App.git](https://github.com/EthanM-programin/Calculator-App.git)
   cd Calculator-App
   ```