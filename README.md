# springboot-public-api-consumer
This project demonstrates how to make a call to any external api using RestTemplate and get the json response ad marshal it in java object. For marshelling purpose, Gson library from Google has been used (Please refer https://github.com/google/gson for more details).

Below publically available api has been used for the test purpose only -

https://jsonplaceholder.typicode.com/users

which returns below json response -

```
  [
    {
      "id": 1,
      "name": "Leanne Graham",
      "username": "Bret",
      "email": "Sincere@april.biz",
      "address": {
        "street": "Kulas Light",
        "suite": "Apt. 556",
        "city": "Gwenborough",
        "zipcode": "92998-3874",
        "geo": {
          "lat": "-37.3159",
          "lng": "81.1496"
        }
      },
      "phone": "1-770-736-8031 x56442",
      "website": "hildegard.org",
      "company": {
        "name": "Romaguera-Crona",
        "catchPhrase": "Multi-layered client-server neural-net",
        "bs": "harness real-time e-markets"
      }
    },

    .......

   ]
 ```
 
 This project reads the response of this api, filters the data and returns below -
 ```
 [
    {
        "lat": "-37.3159",
        "lng": "81.1496",
        "name": "Romaguera-Crona"
    },
    
    ........

]
```
