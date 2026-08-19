import java.util.Scanner;
import java.util.ArrayList;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class Calculator {
    public static void main(String[] args) {
        System.out.println("Welcome to the Quantum-AI Calculator application.");
        System.out.println("Try typing: 'Divide 100 by a quantum number' or 'Add 5 and 10'");

        Scanner scnr = new Scanner(System.in);
        ArrayList<String> history = new ArrayList<>();

        // Modern Java HTTP Client
        HttpClient client = HttpClient.newHttpClient();

        while(true) {
            System.out.print("\nEnter natural language math expression or type exit: ");
            String userInput = scnr.nextLine();

            if(userInput.equalsIgnoreCase("exit")) {
                break;
            }

            if(userInput.equalsIgnoreCase("history")) {
                if(history.isEmpty()) {
                    System.out.println("No calculations yet.");
                } else {
                    for(String record : history) {
                        System.out.println(record);
                    }
                }
                continue;

            }

            try {
                // Safely format the user's input into a JSON string
                String jsonInputString = "{\"query\": \"" + userInput.replace("\"", "\\\"") + "\"}";

                // Build and send the POST request to the Python backend
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://127.0.0.1:8000/api/calculate"))
                        .timeout(Duration.ofSeconds(10))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(jsonInputString))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if(response.statusCode() == 200) {
                    String responseBody = response.body();

                    // Simple manual parsing to avoid needing external JSON libraries (like Jackson/Gson)
                    String resultStr = extractJsonValue(responseBody, "result");
                    String explanationStr = extractJsonValue(responseBody, "explanation");

                    System.out.println("\n[AI Context] " + explanationStr);
                    System.out.println("Result: " + resultStr);

                    history.add(userInput + " -> " + resultStr);

                } else {
                    System.out.println("\n[Error] Invalid input or AI parsing failed.");
                    System.out.println("Server Details: " + response.body());
                }

            } catch(Exception e) {
                System.out.println("\n[Connection Failed] Make sure your Python FastAPI server is running on port 8000.");
            }
            
        }

        System.out.println("Goodbye, and thank you for using the Quantum-AI Calculator application.");
        scnr.close();

    }

    // A lightweight helper to pull specific values out of the Python JSON response
    private static String extractJsonValue(String json, String key) {
        String searchKey = "\"" + key + "\":";
        int startIndex = json.indexOf(searchKey);
        if(startIndex == -1) return "N/A";

        startIndex += searchKey.length();
        int endIndex = json.indexOf(",", startIndex);
        if(endIndex == -1) {
            endIndex = json.indexOf("}", startIndex);
        }

        String value = json.substring(startIndex, endIndex).trim();
        if(value.startsWith("\"") && value.endsWith("\"")) {
            value = value.substring(1, value.length() - 1);
        }
        return value;
        
    }

}