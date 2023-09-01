# Fizz Buzz

The original fizz-buzz consists in writing all numbers from 1 to 100, and just replacing all multiples of 3 by “fizz”, all multiples of 5 by “buzz”, and all multiples of 15 by “fizzbuzz”.

Your goal is to implement a web server that will expose a REST API endpoint that:

- Accepts five parameters : three integers int1, int2 and limit, and two strings str1 and str2.
- Returns a list of strings with numbers from 1 to limit, where: all multiples of int1 are replaced by str1, all multiples of int2 are replaced by str2, all multiples of int1 and int2 are replaced by str1str2.
- The output must look like this : ["1","2","fizz","4","buzz","fizz","7","8","fizz","buzz",...]

Add a statistics endpoint allowing users to know what the most frequent request has been. This endpoint should:

- Accept no parameter
- Return the parameters corresponding to the most used request, as well as the number of hits for this request
- The output must look like this : {"int1":3,"int2":5,"limit":150,"str1":"fizz","str2":"buzz","count":3}

The server needs to be:

- Ready for production
- Easy to maintain by other developers
- Code must be in kotlin ( or java) with Spring oot

Precision :

L’algo est volontairement simple car l’accent sera mis sur la réalisation d’une api PROD ready. Du coup, libre à vous d’ajouter tout ce qu’il vous semble nécessaire afin que l’api soit utilisable en l’état.