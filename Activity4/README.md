# Activity 4 : Architectures Micro-services avec Spring Cloud 
 
## Objectif de l'activité
Créer une application de e-commerce basée sur les micro services :
1. Consul Discovery
2. Spring Cloud Config
3. Spring Cloud Gateway
4. Customer-service
5. Inventory Service
6. Order Service
7. Consul Config (Billing Service)
8. Vault (Billing Service)
9. Frontend Web avec Angular

## Structure du projet

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/3a9f2409-d9da-49e3-a8d3-14ee79a644d6)

### L'execution
+ Pour lancer l'application --> sur votre `IDE`, accéder à votre répertoire du front-end `cd votre_rep` et tapez `ng serve`
+ Tapez `http://localhost:4200/products` ou cliquez sur `products` au niveau du menu de l'application pour voir l'ensemble des produits
  
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/e56ad5c0-b5c7-4d6d-91f1-6b73bddd29d0)

+ Tapez `http://localhost:4200/customers` ou cliquez sur `customers` au niveau du menu de l'application pour voir l'ensemble des customers

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/80be7717-95c9-418d-98f2-41f77b6ebad4)

+ Cliquez sur le button `Orders` pour voir les orders de chaque customer

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/d81014bc-b5a8-41f4-b6c4-5275b13af9c4)

+ Cliquez sur le button `Order Details` pour voir les details d'order

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/439d8ba6-dc70-4388-a6d6-34633321a30e)

### Les comptes rendus des micro-services

+ Le micro-service config
https://github.com/WebProjDeveloper/JEE_All_Activities/tree/master/Activity4/ecom-emsi/config-service

+ Le micro-service customer
https://github.com/WebProjDeveloper/JEE_All_Activities/tree/master/Activity4/ecom-emsi/customer-service

+ Le micro-service inventory
https://github.com/WebProjDeveloper/JEE_All_Activities/tree/master/Activity4/ecom-emsi/inventory-service

+ Le micro-service gateway
https://github.com/WebProjDeveloper/JEE_All_Activities/tree/master/Activity4/ecom-emsi/gateway-service

+ Le micro-service order https://github.com/WebProjDeveloper/JEE_All_Activities/tree/master/Activity4/ecom-emsi/order-service

+ Le micro-service billing https://github.com/WebProjDeveloper/JEE_All_Activities/tree/master/Activity4/ecom-emsi/billing

+ Le micro-service ms-vault
https://github.com/WebProjDeveloper/JEE_All_Activities/tree/master/Activity4/ecom-emsi/ms-vault
