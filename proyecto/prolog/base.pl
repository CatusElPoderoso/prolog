% Definición inicial de tareas y dependencias vacías
:- dynamic gustar/2.

% Añadir un nuevo gusto para un usuario
anadir_gusto(Usuario, Gusto) :-
    \+ gustar(Usuario, Gusto),  % Asegurarse de no duplicar gustos
    assertz(gustar(Usuario, Gusto)).

% Contar gustos en común entre dos usuarios
contar_gustos_comun(User1, User2, Count) :-
    findall(Item, (gustar(User1, Item), gustar(User2, Item)), CommonItems),
    length(CommonItems, Count).

% Verificar si dos usuarios tienen al menos la mitad de gustos en común
gustos_similares(User1, User2) :-
    findall(Item, gustar(User1, Item), Items1),
    length(Items1, Length1),
    findall(Item, gustar(User2, Item), Items2),
    length(Items2, Length2),
    contar_gustos_comun(User1, User2, CommonCount),
    CommonCount >= min(Length1, Length2) // 2.

% Recomendación basada en gustos similares
recomendar(User, Recommendations) :-
    findall(OtherUser, (gustos_similares(User, OtherUser), User \= OtherUser), SimilarUsers),
    findall(Item, (member(SimilarUser, SimilarUsers), gustar(SimilarUser, Item), \+ gustar(User, Item)), AllRecommendations),
    sort(AllRecommendations, Recommendations).  % Eliminar duplicados
