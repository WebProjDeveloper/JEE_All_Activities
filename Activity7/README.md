# Activity 7 : Sécurité Oauth2, OIDC, JWT, Keycloak

## Part 1 / Objectif :

```java
1. Télécharger Keycloak 23
2. Démarrer Keycloak
3. Créer un compte Admin
4. Créer une Realm
5. Créer un client à sécuriser
6. Créer des utilisateurs
7. Créer des rôles
8. Affecter les rôles aux utilisateurs
9. Avec PostMan :
    - Tester l'authentification avec le mot de passe
    - Analyser les contenus des deux JWT Access Token et Refresh Token
    - Tester l'authentification avec le Refresh Token
    - Tester l'authentification avec Client ID et Client Secret
    - Changer les paramètres des Tokens Access Token et Refresh Token
```

### Réalisation du travail :
```java
- Les clients qu'on a créé :
```
![6](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/9c3deb56-4213-4d12-8eec-82ed555d2dd6)

```java
- Les utilisateurs qu'on a créé :
```
![7](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/76aa966f-74d7-44b2-b7af-dcb031313dfb)

```java
- Les roles : 
```
![8](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/0d885aa4-4920-440e-8e33-fc88055c96ee)

```java
- La realm :
```
![4](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/c0c41903-ea96-46c3-82f6-dd4008ecba55)

```java
- Test de l'authentification avec le mot de passe :
```
![1](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/2b43857d-d2b3-4b52-a89c-fbd62e5e9fbc)

```java
- Test de l'authentification avec le Refresh Token :
``` 
![2](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/620707e8-12ae-45a9-a272-d9ca861488b8)

```java
- Test de l'authentification avec Client ID et Client Secret :
```
![3](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/75764ccf-2b4c-4053-b37e-5c601c2621d1)


## Part 2 / Objectif :
```java
Développer et sécuriser une application basée sur les micro-services en utilsant Oauth 2, OIDC, Keycloak :
   - Invenroty-service
   - Frontend Thymeleaf
   - Frontend Angular
```
### Réalisation du travail : 
 
```java
- Assurez vous d'avoir télécharger en avance keycloak
- Lancer keycloak sur l'invite de commandes en tapant ./kc.sh start-dev
- Tapez `localhost:8080`
- Après dans la console d'admin, créer un compte admin
```

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/f1ff989e-8ff4-48aa-8164-5c54f4dd5a2e)


```java
- Créer une Realm appelée sdia-realm 
```

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/c1272cf7-6c8c-4fb8-bb0f-235e7fe3cfc9)


```java
- Créer un client appelé sdia-customer-client
```

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/fb484800-245c-4853-8712-0d39a7e06405)

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/fd0c0873-0a4c-462f-8753-f321607d62e2)

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/a4ea471f-cbb0-46d9-9a6f-e971397f1c2d)

```java
- Créer les roles nécessaires
```

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/32e4e1d0-01f7-4ca0-a6ae-100bb164f997)

```java
- Créer les utilisateurs
```

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/30185295-0815-4f95-961f-dc46a213cd13)

```java
- Assignez les roles aux utilisateurs
```

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/a8560296-d899-4c70-bad8-8336650a85cf)

```java
- La configuration de la realm
```

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/f713beae-6033-4169-b400-7de133376c5e)

```java
- Les informations en ce qui concerne le refresh token & access token
```

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/bfdcc7e4-d361-4f30-bf97-7bebe4374977)

### Partie Frontend with thymeleaf : 

```java
- La 1ère interface qui s'affiche, lorsqu'on tape dan sle navigateur localhost:8090
- Dans cette interface on doit se connecter pour pouvoir accéder aux ressources à savoir les clients de l'application ainsi que les produits
- On a trois choix pour la connexion soit par google, github ou keycloak
```

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/23527583-3f05-49b5-8796-3c65a314d02f)

```java
- Si on veut se connecter avec google
```
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/485d9575-b8a8-4d26-b5ab-c743859dd034)

```java
- Si on veut se connecter avec github
```
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/e5193b75-168b-400a-a088-f1539fe4f994)

```java
- On va se connecter avec keycloak avec le compte du user2 qui a le role admin
```
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/4bc8bc74-5916-4193-9329-0f3fecf1ae06)

```java
- Tapez localhost:8090/customers ou cliquez sur customers pour voir la liste des clients
```
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/4c3bfe06-e708-4068-97d6-7cb13364cfd6)

```java
- Tapez localhost:8090/products ou cliquez sur products pour voir la liste des produits
```
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/a52711b7-440e-480b-b993-1ef212588975)

```java
- Pour user1 qui a le role d'un simple utilisateur, il n'est pas autorisé à voir la liste des clients
```
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/4da2e9f3-1d3a-4516-9b43-7e13f2091985)

```java
- Essayons de créer un utilisateur avant de configurer la politique de mot de passe
```
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/c5615256-bf25-4ee6-aefc-1db8755423d9)

```java
- Tapez localhost:8090/auth
```
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/e294466d-aa35-47ef-8770-e14b5c5bf0d1)

```java
- Configurons la politique de mot de passe
```
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/a5f2e47f-22b0-45a0-91eb-91efac94f7d4)

```java
- Remarquons que maintenant on doit respecter la politique de mot de passe qu'on a ajouté
```
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/a6bdb9b8-d408-40de-b603-2176838fce93)

### Partie Frontend with angular : 
```java
- Le client pour la partie angular
```
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/5c20451e-5820-4b1f-9a57-c21ae0dbbe7a)

![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/c791fb8b-e00a-49d7-96e6-755c808f8101)

```java
- Tapez localhost:4200/customers ou cliquez sur customers pour voir la liste des clients
```
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/61a9ac0b-411e-4d57-b653-fd77ede1a65f)

```java
- Tapez localhost:4200/products ou cliquez sur products pour voir la liste des produits
```
![image](https://github.com/WebProjDeveloper/JEE_All_Activities/assets/125798807/e15d15fa-c6f4-48d1-b88f-825cf3d599e7)













