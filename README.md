# examen_moviles_A01711231
Examen TC2007B

Mateo Minghi

A01711231.

Android


---

Este proyecto sigue los conceptos utilizados en clase:

arquitectura mvvm + clean architecture (capas data, domain, presentation)

Hilt
Retrofit


MyApplication/
├── gradle/
│   └── libs.versions.toml       <-- Catálogo de versiones (Centraliza dependencias)
├── build.gradle.kts (Project)   <-- Configuración global
├── app/
│   ├── build.gradle.kts (Module)<-- Plugins (Hilt, KSP), dependencias de la app
│   ├── src/
│   │   ├── main/
│   │   │   ├── AndroidManifest.xml
│   │   │   ├── res/             <-- Recursos (strings, themes, icons)
│   │   │   └── java/
│   │   │       └── com.tuempresa.tuapp/
│   │   │           │
│   │   │           ├── MyApplication.kt      <-- Anotada con @HiltAndroidApp
│   │   │           ├── MainActivity.kt       <-- @AndroidEntryPoint (Contenedor NavHost)
│   │   │           │
│   │   │           ├── 📂 di/                <-- INYECCIÓN DE DEPENDENCIAS
│   │   │           │   └── AppModule.kt      <-- Provee Retrofit, Room, Repositorios
│   │   │           │
│   │   │           ├── 📂 data/              <-- CAPA DE DATOS (Externa)
│   │   │           │   ├── 📂 remote/
│   │   │           │   │   ├── 📂 api/
│   │   │           │   │   │   └── MyApiService.kt  <-- Interfaz de Retrofit (@GET, @POST)
│   │   │           │   │   └── 📂 dto/
│   │   │           │   │       └── ItemDto.kt       <-- Modelo crudo del JSON (Data Transfer Object)
│   │   │           │   ├── 📂 mapper/
│   │   │           │   │   └── DataMappers.kt       <-- Funciones: Dto.toDomain()
│   │   │           │   └── 📂 repository/
│   │   │           │       └── ItemRepositoryImpl.kt <-- Implementa la interfaz de Domain
│   │   │           │
│   │   │           ├── 📂 domain/            <-- CAPA DE DOMINIO (Reglas de Negocio - Pura)
│   │   │           │   ├── 📂 model/
│   │   │           │   │   └── Item.kt              <-- Data class limpia (sin anotaciones JSON)
│   │   │           │   ├── 📂 repository/
│   │   │           │   │   └── ItemRepository.kt    <-- Interfaz (Contrato)
│   │   │           │   ├── 📂 usecase/
│   │   │           │   │   └── GetItemsUseCase.kt   <-- Lógica encapsulada (Retorna Flow)
│   │   │           │   └── 📂 common/
│   │   │           │       └── Result.kt            <-- Sealed class (Success, Error, Loading)
│   │   │           │
│   │   │           └── 📂 presentation/      <-- CAPA DE UI (Compose)
│   │   │               ├── 📂 navigation/
│   │   │               │   ├── NavGraph.kt          <-- Define el NavHost y composables
│   │   │               │   └── AppScreens.kt        <-- Sealed class con las rutas
│   │   │               ├── 📂 common/               <-- Componentes UI reusables
│   │   │               │   ├── ErrorView.kt
│   │   │               │   └── LoadingShimmer.kt
│   │   │               ├── 📂 screens/
│   │   │               │   ├── 📂 home/
│   │   │               │   │   ├── HomeScreen.kt    <-- UI Principal
│   │   │               │   │   ├── HomeViewModel.kt <-- @HiltViewModel
│   │   │               │   │   └── HomeUiState.kt   <-- Estado de la vista (isLoading, data, error)
│   │   │               │   └── 📂 detail/
│   │   │               │       ├── DetailScreen.kt
│   │   │               │       └── ...
│   │   │               └── 📂 theme/
│   │   │                   ├── Color.kt
│   │   │                   └── Theme.kt