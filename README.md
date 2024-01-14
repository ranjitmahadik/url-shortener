### **Unique Short URLs**
One of the key considerations in our URL Shortener is the handling of duplicate long URLs. We've made a deliberate choice to prioritize unique short URLs for each distinct long URL. While reusing short URLs for identical long URLs may seem like a storage optimization, we believe that maintaining uniqueness serves our project goals better.

### **Reasons for Unique Short URLs**  
- **Analytics Accuracy**: Reusing short URLs for the same long URL can lead to inaccurate analytics. By assigning a unique short URL to each long URL, we ensure that analytics data accurately represents individual user interactions with distinct content.  
- **User Experience**: Unique short URLs contribute to a better user experience. Users can rely on the consistency of short URLs, knowing that each one corresponds to a specific long URL.  
- **Security**: Unique short URLs add an additional layer of security. They prevent potential abuse or confusion that might arise from the reuse of short URLs for different long URLs.

### How to Generate Unique Short URLS everytime?
1. Use Hashing Techniques
2. Use Hashing Techinique with Collision Handling mechanism.
3. UUID's
4. Use ID Generator  
   3.1 Ticket Server (similar to flicker)  
   3.2 Twitter Snowflake

### Let's discuss each of them in detail and see which fits better our use case.   
1. **Hashing Techniques:**
   - *Pros:* Provides a deterministic and unique output for each input. Fast and efficient.
   - *Cons:* Possibility of hash collisions.

2. **Hashing Technique with Collision Handling Mechanism:**
   - *Pros:* Maintains the speed of hashing while addressing collision issues.
   - *Cons:* Requires additional logic to handle collisions. Increased complexity in implementation.

3. **UUIDs (Universally Unique Identifiers):**
   - *Pros:* Globally unique without the need for a central authority. Simple to implement.
   - *Cons:* Longer representation compared to traditional hashes. difficult to index.

4. **ID Generator:**
   4.1. **Ticket Server (Similar to Flickr):**
      - *Pros:* Scalable and distributed. Low collision probability.
      - *Cons:* Dependency on a central server.

   4.2. **Twitter Snowflake:**
      - *Pros:* Distributed and scalable. No need to central server.
      - *Cons:* Limited by the snowflake size.
  
  **we are going to use Snowflake approach as they are easy to scale, maintain and implement.**

### How to Setup this Project?
1. Make sure you are at root path of this project.
2. Run mvnw clean and install command to generate jar file.
```
    ./mvnw clean install
```
3. once jar file is created, now just run docker compose up.
```
   docker compose up -d
```

### How to Generate Short Urls?
- POST Request for generating short urls.
  
  ```
     end-point: 127.0.0.1:8080/v1/api/url/shorten
     body:   {
                "fullUrl": "https://www.google.com"
             }
  ```
- GET Request for full url.

   ```
      end-point: 127.0.0.1:8080/v1/api/url/<short-url-code>
      eg.  127.0.0.1:8080/v1/api/url/70POGa20
   ```
