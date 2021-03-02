package com.project.OPENWEATHER.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.expression.ParseException;

import com.project.OPENWEATHER.model.URLgeneration;


public class Download {

		public static void main(String[] args) {
			
			
			ServiceApplication sa = new ServiceApplication();
			
			URLgeneration u = new URLgeneration();
			String url = u.getUrl();
			String ApiKey = u.getApiKey();
			ServiceApplication ss = new ServiceApplication() {
				
			};
			String s = ss.URLgenerator(null);
			
			
			try {

				HttpURLConnection openConnection = (HttpURLConnection) new URL(url).openConnection();
				openConnection.setRequestMethod("POST");
				openConnection.setRequestProperty("Authorization",
						"Bearer -"+ ApiKey+"_");
				openConnection.setRequestProperty("Content-Type", "application/json");
				openConnection.setRequestProperty("Accept", "application/json");
				openConnection.setDoOutput(true);
				
				//se si copia e incolla da ostma e si incolla qui sotto nelle  virgolette l'indentatura è automaica
				/*
				 * String jsonBody = "{\r\n" + "    \"path\": \"\",\r\n" + "    \"recursive\": true,\r\n"
						+ "    \"include_media_info\": false,\r\n" + "    \"include_deleted\": false,\r\n"
						+ "    \"include_has_explicit_shared_members\": false,\r\n"
						+ "    \"include_mounted_folders\": true,\r\n" + "    \"include_non_downloadable_files\": true\r\n"
						+ "}";
						*/
				String jsonBody = "{\r\n" + 
						"    \"path\": \"/Photos/Sample Album/img001.jpg\",\r\n" + 
						"    \"include_media_info\": true,\r\n" + 
						"    \"include_deleted\": false,\r\n" + 
						"    \"include_has_explicit_shared_members\": false\r\n" + 
						"}";

				try (OutputStream os = openConnection.getOutputStream()) {
					byte[] input = jsonBody.getBytes("utf-8");
					os.write(input, 0, input.length);
				}

				InputStream in = openConnection.getInputStream();

				String data = "";
				String line = "";
				try {
					InputStreamReader inR = new InputStreamReader(in);
					BufferedReader buf = new BufferedReader(inR);

					while ((line = buf.readLine()) != null) {
						data += line;
						System.out.println(line);
					}
				} finally {
					in.close();
				}
				JSONObject obj = (JSONObject) JSONValue.parseWithException(data);
				System.out.println("OK");
			} catch (IOException | ParseException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
