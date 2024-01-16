# Activity 6 : KAFKA et Event Driven Architecture


#### Objectif de l'activité
```java
1. Tester avec Kafka-console-producer et kafka-console-consumer
2. Avec Docker (voir https://developer.confluent.io/quickstart/kafka-docker/) https://www.youtube.com/watch?v=9O1Kuk2xXO8
 - Créer le fichier docker-compose.yml
 - Démarrer les conteneurs docker : zookeeper et kafka-broker
 - Tester avec Kafka-console-producer et kafka-console-consumer
3. En Utilisant KAFKA et Stpring Cloud Streams, Créer : https://www.youtube.com/watch?v=eo8pSWpj2os&authuser=0
- Un Service Producer KAFKA via un Rest Controler
- Un Service Consumer KAFKA
- Un Service Supplier KAFKA
- Un Service de Data Analytics Real Time Stream Processing avec Kaflka Streams
- Une application Web qui permet d'afficher les résultats du Stream Data Analytics en temps réel
```

## Part 1 :

- Démarrer Kafka :
  
![Capture9](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/36ec3759-330b-42eb-8c40-6ea77a48f74d)


- Démarrer Zookeeper :
  
![Capture10](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/34edfb9e-0817-4ddf-8341-c80056a16a1a)


- Lancer Kafka-console-producer et kafka-console-consumer :
  
![Capture6](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/905ee285-d128-4acd-9558-e18b67caa373)


- Tester le bon fonctionnement de la communication entre Kafka-console-producer et kafka-console-consumer :
  
![Capture1](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/dde1f529-a087-408e-95e4-3b6562e63c39)


- Accéder à `localhost:8082/publish/R1/blog` pour visualiser les informations sur le navigateur :
  
![Capture2](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/11222db4-3b06-458b-8b2d-72dcd9767d83)


- Accéder à `localhost:8082/publish/R1/blog` et remarquer que la duration a été changée :
    
![Capture3](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/143267da-0ad5-4b84-b875-3c7c8765a81f)


- Pour accéder tapez `localhost:8082/publish/R1/contact` :
  
![Capture4](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/d905c234-6760-424a-a527-f53555df979e)


- Pour accéder tapez `localhost:8082/publish/R1/contacts` :
  
![Capture5](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/88776b3c-2e42-4b8d-911e-51bc0f0ceb9d)


- Kafka-console-producer :
  
![Capture11](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/d7130d7e-1e6c-4bc6-a249-f2c4f85e7114)


- Le 1er Kafka-console-consumer :
  
![Capture8](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/1ee99479-05fb-4a7c-968e-e82e19e4154f)


- Le 2ème Kafka-console-consumer :

![Capture12](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/0730675d-9707-4fef-90c2-9ce7d0491e30)


- Le 3ème Kafka-console-consumer :
  
![Capture13](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/11f03861-4b8b-403f-811c-a34967577e55)

## Part 2 : 

- Pour éxecuter le fichier, tapez la commande `docker-compose up` :
  
![c1](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/a4620ffa-72ea-4ac6-b2c4-a4b613d5172e)


- Tapez `docker ps` pour afficher les informations sur les conteneurs et leurs images :
  
![c2](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/79d10259-6d19-4cd5-813f-ba9dba0c8a3a)


- Tester kafka-console-producer :
  
![c4](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/dc5d977c-0dc1-44dc-8d1a-5d1c44f65de9)


- Tester kafka-console-consumer :
  
![c3](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/4612eb6e-38ef-41cd-8e5e-4d186d21e7b8)


- La traçe :
  
![c5](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/5e6ea70a-75a0-4b0a-8a90-c88037fff6a6)


## Part 3 : 

- Tapez `localhost:8080/analytics` :
  
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/86b9769c-a3ab-4738-8682-70cb70e9e354)


- Tapez `localhost:8080` :
  
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/e2fbcfb7-f0bf-4e62-8552-6a9461020437)


