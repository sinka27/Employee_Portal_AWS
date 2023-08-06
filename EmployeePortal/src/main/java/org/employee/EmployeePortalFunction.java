package org.employee;

import java.util.HashMap;
import java.util.Map;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
public class EmployeePortalFunction {
    // Create an instance of AmazonDynamoDB client
    private static final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard().build();
    private static final DynamoDB dynamoDB = new DynamoDB(client);
    private static final String tableName = "EmployeeTable";

    // Define the handler function that the Lambda service will use as an entry point
    public Map<String, Object> employeeHandler(Map<String, String> input){
        // Extract the id, name,phone and email from the Lambda service's input object
        int id = Integer.parseInt(input.get("id"));
        String name = input.get("name");
        String phone = input.get("phone");
        String email = input.get("email");

        // Write the id, name, phone,email to the DynamoDB table
        Table table = dynamoDB.getTable(tableName);
        Item item = new Item().withPrimaryKey("ID", Integer.valueOf(id))
                .withString("Name", name)
                .withString("Phone", phone)
                .withString("Email", email);
        table.putItem(item);
        // Create a response object
        Map<String, Object> response = new HashMap<>();
        response.put("statusCode", 200);
        response.put("body", "Your information is successfully uploaded");
        return response;
    }

    //method to get Item from DynamoDb
//    public String getItem(Map<String, String> input){
//        int id = Integer.parseInt(input.get("id"));
//        Table table = dynamoDB.getTable(tableName);
//        Item item = table.getItem("id", id);
//        return item.getString("name", "phone", "email");
//    }

    //method to delete Item from DynamoDb
    // public String deleteItem(Map<String, String> input){
    //     String id = input.get("ID");
    //     Table table = dynamoDB.getTable(tableName);
    //     table.deleteItem("ID", id);
    //     return "Item deleted";
    // }
}