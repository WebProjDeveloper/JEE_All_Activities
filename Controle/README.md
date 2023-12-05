## Compte rendu pour le controle : Activité 4 & 5

### Lien vers le code et le compte rendu détaillé de l'activité 4 ->
https://github.com/WebProjDeveloper/JEE_All_Activities/tree/master/Activity4

#### Objectif de l'activité
```java
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
```

#### L'execution
+ Pour lancer l'application --> sur votre `IDE`, accéder à votre répertoire du front-end `cd votre_rep` et tapez `ng serve`
+ Tapez `http://localhost:4200/products` ou cliquez sur `products` au niveau du menu de l'application pour voir l'ensemble des produits
  
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/e56ad5c0-b5c7-4d6d-91f1-6b73bddd29d0)

+ Tapez `http://localhost:4200/customers` ou cliquez sur `customers` au niveau du menu de l'application pour voir l'ensemble des customers

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/80be7717-95c9-418d-98f2-41f77b6ebad4)

+ Cliquez sur le button `Orders` pour voir les orders de chaque customer

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/d81014bc-b5a8-41f4-b6c4-5275b13af9c4)

+ Cliquez sur le button `Order Details` pour voir les details d'order

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/439d8ba6-dc70-4388-a6d6-34633321a30e)

#### Les comptes rendus des micro-services
```java
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
```

## Lien vers le code et le compte rendu détaillé de l'activité 5 ->
https://github.com/WebProjDeveloper/JEE_All_Activities/tree/master/Activity5

#### Objectif de l'activité
```java
Créer une application web basée sur Angular qui permet de gérer les produits.
Chaque Produit est défini par son id, name, price, quantity, available.
Le backend de l'application est basé sur une REST API basée sur Json-Server
L'application doit permettre de :
    - Afficher les produits
    - Chercher les produits
    - Faire la pagination
    - Supprimer un produits
    - Editer un produit
    - Mettre à jour un produit
    - Faire l'authentification et protéger les routes
```
#### Pour l'admin :
- Login :
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/de3f8489-9f31-4814-8d28-938358d1746a)


- Home component :
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/4fdb0e7c-8d12-4cc6-8d05-a5e62afe0d0d)


- Products component :
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/0180f1ab-a27d-4264-ad15-a13f7fe69a86)


- Liste des produits :
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/f6bec4df-895e-4cce-a1bf-3e0c82826482)


- La recherche :
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/ca85edfc-3aa6-4d0d-8df2-6f85940552e7)


- L'ajout d'un produit :
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/7c261128-9087-4d0c-9e84-0cd6f988c3a7)


- Produit ajouté avec succès :
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/6dfba4ab-965b-46ca-ae67-63c4c868b243)


#### Pour un simple utilisateur :
- Login :
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/ccbfb244-751e-47f0-a5a0-590d7497a471)


- Home component du simple utilisateur :
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/7727dbf2-7ad1-47db-a636-e1bc4682aa09)


- Liste des produits :
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/c199dc48-cdf4-4065-b4aa-21297c03446f)


- La recherche :
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/64d8a61e-c326-40c6-8ffa-e03748984ea2)

- Un simple utilisateur n'est pas autorisé à faire des manipulations comme l'ajout, la modification et la suppression :
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/a513ab43-63e9-4529-9a34-b11347cc4c21)



