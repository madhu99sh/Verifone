1. Layers in Clean Architecture
UI (Compose)
│
▼
Presentation (ViewModel)
│
▼
Domain (UseCase)
│
▼
Data (Repository → RemoteDataSource)




High-Level Flow Description

User Input
User types in a search box (e.g., SearchBar in Compose).
UI → ViewModel Communication
SearchBar calls viewModel.onQueryChanged(query) on each text change.

ViewModel Logic

A MutableStateFlow captures input.

Uses debounce(300ms) and distinctUntilChanged() to limit API calls.
Cancels previous jobs using flatMapLatest.
Triggers a use case with the final debounced query.

UseCase Layer
Calls the repository to fetch products from an API, applying pagination.

Repository Layer
Uses Retrofit/Ktor to call API and return a list of paginated products.

Result Propagation
ViewModel updates UI state (e.g., UiState.Success(data)), collected in the UI layer.

Compose UI
Observes ViewModel’s StateFlow and recomposes the product list.





Sequence Diagram (Debounced Search)

[Product]
  ↓ types text
[Compose UI]
  ↓ calls viewModel.onQueryChanged(text)
[ViewModel]
  ↓ emits to queryFlow
  → debounce(300ms), distinctUntilChanged
  → cancel previous → call UseCase
[UseCase]
  → call repository.searchProducts(query, limit, skip)
[Repository]
  → perform API call
  ← returns result
[ViewModel]
  → updates UI state
[Compose UI]
  ← displays new list
