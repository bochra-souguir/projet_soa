# Frontend REST - TP333 Gestion des Personnes

Application web frontend consommant le backend REST JAX-RS avec JDBC développé en TP.

## 🎯 Technologies

- **HTML5/CSS3/JavaScript** - Frontend pur
- **Bootstrap 5.3** - UI responsive
- **Fetch API** - Appels REST
- **Backend:** JAX-RS (Jersey) + JDBC + MySQL

## 🚀 Fonctionnalités

✅ **Lister** toutes les personnes  
✅ **Ajouter** une personne (nom + âge)  
✅ **Modifier** une personne  
✅ **Supprimer** avec confirmation  
✅ **Rechercher** par ID ou nom  
✅ Validation des formulaires  
✅ Messages de retour en temps réel

## ⚙️ Installation & Configuration

### 1. Backend (Tomcat)
```bash
# Assurez-vous que:
- MySQL est démarré (base: tptest)
- Tomcat est démarré
- Le projet tp333 est déployé
```



### 3. URLs
- **Backend API:** `http://localhost:8085/tp333/api/users`
- **Frontend:** `http://localhost:8085/tp333/index.html`

## 🔌 Endpoints REST Utilisés

| Méthode | URL | Description |
|---------|-----|-------------|
| GET | `/api/users/affiche` | Liste toutes les personnes |
| GET | `/api/users/{id}` | Récupère par ID |
| GET | `/api/users/search?name=X` | Recherche par nom |
| POST | `/api/users/add` | Ajoute (JSON body) |
| PUT | `/api/users/update/{id}` | Modifie (JSON body) |
| DELETE | `/api/users/remove/{id}` | Supprime |



tp333/
├── src/
│   └── com.info/
│       ├── db/
│       ├── model/
│       ├── router/
│       └── service/
└── WebContent/
    └── index.html  ← Frontend
```
