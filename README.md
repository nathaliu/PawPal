<h3 align="center">📱 Application Android - Pawpal</h3>

<p align="center">
  <img src="https://img.shields.io/badge/Android_Studio-3DDC84?style=flat-square&logo=android-studio&logoColor=white" />
  <img src="https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white" />
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white" />
  <img src="https://img.shields.io/badge/XML-FF6600?style=flat-square&logo=xml&logoColor=white" />
</p>

---

### 📝 Le Concept
**PawPal** est un projet académique réalisé en binôme. L'application a pour but de mettre en relation les propriétaires d'animaux de compagnie avec des promeneurs locaux en temps réel grâce à la géolocalisation. 

🚧 Note de développement : Ce projet a été réalisé dans un temps imparti (cadre scolaire) et fait office de Proof of Concept (POC). L'architecture de base et l'UI sont posées, mais certaines fonctionnalités (comme la messagerie en temps réel) n'ont pas été finalisées.

---

### ✨ Fonctionnalités implémentées

* 🎨 **Interface ergonomique & Responsive :** Navigation fluide via une `BottomNavigationView`. L'interface s'adapte aux modes portrait et paysage, et est disponible en Français et en Anglais.
* 🗺️ **Cartographie (Google Maps) :** Affichage de la position de l'utilisateur sur une carte interactive, ainsi que la position des promeneurs à proximité.
* 📸 **Appareil Photo & Galerie :** Possibilité de prendre des photos directement depuis l'application ou d'en importer depuis la galerie pour personnaliser les profils.
* 🗄️ **Base de données distante :** Sauvegarde et lecture des données via une base de données MySQL externe.

---

### 🏗️ Architecture Technique
L'application est structurée de manière modulaire pour séparer la logique de l'interface :

* **MainActivity :** Point d'entrée gérant la navigation principale et l'initialisation de l'UI.
* **Fragments (Navigation) :**
  * `HomeFragment` : Page d'accueil de l'application.
  * `MapsFragment` : Intégration de l'API Google Maps et gestion des marqueurs.
  * `ChatFragment` : Interface prévue pour la messagerie.
  * `ProfileFragment` : Gestion des informations de l'utilisateur et de son animal.
* **DatabaseHelper :** Gère les opérations CRUD (Create, Read, Update, Delete) pour la communication avec MySQL.

---

### 🚀 Installation et Lancement
Pour tester l'application en local :

1. Clonez ce dépôt :
   ```bash
   git clone https://github.com/nathaliu/PawPal.git
2. Ouvrez le projet dans Android Studio.

4. Laissez Gradle synchroniser les dépendances.

3. Lancez l'application sur un émulateur (AVD) ou un appareil physique Android.

### 🔮 Améliorations futures envisagées
- Migration vers une base de données locale (SQLite) pour le mode hors-ligne.
- Affichage de la position des utilisateurs en temps réel.
- Système de notifications et messagerie instantanée fonctionnelle.

<p align="center">
  <br>
  <i>Projet réalisé par Nathalie LIU & Steven VAN</i>
</p>
