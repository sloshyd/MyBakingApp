package uk.co.sloshyd.mybakingapp;

import android.media.DeniedByServerException;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import uk.co.sloshyd.mybakingapp.data.IngredientsData;
import uk.co.sloshyd.mybakingapp.data.InstructionsData;
import uk.co.sloshyd.mybakingapp.data.RecipeData;

/**
 * Created by Darren on 25/01/2018.
 */

public class Utils {

    private static final String TAG = Utils.class.getSimpleName();
    public static final String DATA_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private static final int OK_SERVER_RESPONSE_CODE = 200;
    private static final int TIME_OUT_LIMIT = 2000;
    private static final String DELIMITER = "\\A";

    public static ArrayList<RecipeData> getRecipes(String response) {
        ArrayList<RecipeData> recipeData = new ArrayList<>();
        if(response == null){
            return recipeData;
        }

        try {
            JSONArray root = new JSONArray(response);
            recipeData = new ArrayList<>();

            for(int i = 0; i< root.length();i++){
                //data to be collected
                String recipeId;
                String recipeName;
                String serving;
                String image;

                JSONObject recipe = root.getJSONObject(i);
                recipeId = recipe.getString("id");
                recipeName = recipe.getString("name");
                serving = recipe.getString("servings");
                image = recipe.getString("image");
                recipeData.add(new RecipeData(recipeId, recipeName, serving, image));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return recipeData;
    }

    public static ArrayList<IngredientsData> getIngredients(String response) {
        ArrayList<IngredientsData> ingredientsData = new ArrayList<>();
        if(response == null){
            return ingredientsData;
        }

        JSONArray root = null;
        try {
            root = new JSONArray(response);
            for(int i = 0; i < root.length();i++){
                JSONObject recipeObject = root.getJSONObject(i);
                String recipeIdentifier = recipeObject.getString("id");
                JSONArray ingredients = recipeObject.getJSONArray("ingredients");
                for(int j= 0; j <ingredients.length();j++){

                    //get data
                    String quantity;
                    String measurement;
                    String ingredient;

                    JSONObject ingredientObject = ingredients.getJSONObject(j);
                    quantity = ingredientObject.getString("quantity");
                    measurement = ingredientObject.getString("measure");
                    ingredient = ingredientObject.getString("ingredient");

                    ingredientsData.add(new IngredientsData(recipeIdentifier, quantity, measurement, ingredient));

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ingredientsData;
    }

    public static ArrayList<InstructionsData> getInstructions(String response) {
        ArrayList<InstructionsData> instructionsData = new ArrayList<>();
        if(response == null){
            return instructionsData;
        }


        try {
            JSONArray root = new JSONArray(response);
            for(int i = 0; i < root.length();i++){
                JSONObject recipeObject = root.getJSONObject(i);
                String recipeIdentifier = recipeObject.getString("id");
                JSONArray instructions = recipeObject.getJSONArray("steps");
                for(int j= 0; j <instructions.length();j++){

                    //get data
                    String stepNumber;
                    String shortDescription;
                    String description;
                    String videoUrl;
                    String thumbnail;

                    JSONObject instructionsObject = instructions.getJSONObject(j);
                    stepNumber = instructionsObject.getString("id");
                    shortDescription = instructionsObject.getString("shortDescription");
                    description = instructionsObject.getString("description");
                    if(instructionsObject.isNull("videoURL")){
                        videoUrl = "";
                    } else{
                        videoUrl = instructionsObject.getString("videoURL");
                    }
                    if(instructionsObject.isNull("thumbnail")){
                        thumbnail = "";
                    } else{
                        thumbnail = instructionsObject.getString("thumbnail");
                    }

                    instructionsData.add(new InstructionsData(recipeIdentifier, stepNumber,shortDescription,description,videoUrl,thumbnail));

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return instructionsData;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException, DeniedByServerException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setConnectTimeout(TIME_OUT_LIMIT);

        InputStream in = null;
        try {

            in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter(DELIMITER);

            if (urlConnection.getResponseCode() != OK_SERVER_RESPONSE_CODE) {
                throw new DeniedByServerException("Server not responding");

            } else {
                boolean hasInput = scanner.hasNext();
                if (hasInput) {
                    return scanner.next();
                } else {
                    return null;
                }
            }

        } finally {
            close(urlConnection, in);
        }
    }

    //method is only called to close connection and inputstream at the end of getting the server data
    private static void close(HttpURLConnection connection, InputStream inputStream) {
        if (connection != null) {
            connection.disconnect();
        }
        try {
            if (inputStream != null) {
                inputStream.close();
            }
        } catch (IOException e) {
            Log.e(TAG, "Error closing input stream " + e);
        }
    }

    public static URL getDataURL (String url) throws MalformedURLException {

        return new URL(DATA_URL);
    }


    public static int getRandomNumber(int max) {

        Random random = new Random();
        return random.nextInt(max);
    }

    public static String getDataFromServer() {
        String response = null;

        try {
            URL dataUrl = Utils.getDataURL(Utils.DATA_URL);
            response = Utils.getResponseFromHttpUrl(dataUrl);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DeniedByServerException e) {
            e.printStackTrace();
        }
        //remove non ASCII char
        response = response.replaceAll("[^\\p{ASCII}]", "");
        return response;
    }
}
