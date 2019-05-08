% Lista 3 de Haskell em Prolog

% 1. Usando list comprehension, defina uma função add10toall :: [Int] -> [Int], 
% que receba uma lista e adicione o valor 10 a cada elemento dessa lista, produzindo outra lista.

add10toall([], []).
add10toall([H1|T1], [H2|T2]) :-
H2 is H1 + 10,
add10toall(T1, T2).

% 2. Usando list comprehension, defina uma função multN :: Int -> [Int] -> [Int], 
% que receba um número N e uma lista, e multiplique cada elemento da lista por N, produzindo outra lista.

multN(_, [], []).
multN(N, [H1|T1], [H2|T2]) :-
H2 is H1 * N,
multN(N, T1, T2), !.

% 3. Usando list comprehension, defina uma função applyExpr :: [Int] -> [Int], 
% que receba uma lista e calcule 3*x+2 para cada elemento x da lista, produzindo outra lista.

applyExpr([],[]).
applyExpr([H1|T1], [H2|T2]) :-
H2 is H1 * 3 + 2,
applyExpr(T1,T2).

% 4. Usando list comprehension, escreva uma função addSuffix :: String -> [String] -> [String] ,
%  para adicionar um dado sufixo às strings contidas numa lista. Exemplo:

addSuffix(_, [], []).
addSuffix(S, [H1|T1], [H2|T2]) :-
string_concat(H1, S, H2),
addSuffix(S, T1, T2), !.

% 5. Usando list comprehension, defina uma função selectgt5 :: [Int] -> [Int], 
% que receba uma lista e selecione somente os valores maiores que 5, produzindo outra lista.

selectgt5([],[]).
selectgt5([H1|T1], [H2|T2]) :- H1 > 5, H2 is H1, selectgt5(T1, T2).
selectgt5([_|T1], L2) :- selectgt5(T1, L2), !.


% 6. Usando list comprehension, defina uma função sumOdds :: [Int] -> Int, 
% que receba uma lista e obtenha o somatório dos valores ímpares, produzindo outra lista. Pesquise funções auxiliares que manipulem listas.

sumOdds(L, SOMA) :-
sumOdds1(L, L2),
sum_list(L2, SOMA), !.

isOdd(N) :-
X is mod(N, 2),
X = 1.

sumOdds1([],[]).
sumOdds1([H1|T1], [H2|T2]) :- isOdd(H1), H2 is H1, sumOdds1(T1, T2), !.
sumOdds1([_|T1], L2) :- sumOdds1(T1, L2), !.

% 7. Usando list comprehension, defina uma função selectExpr :: [Int] -> [Int],
% que receba uma lista e selecione somente os valores pares entre 20 e 50, produzindo outra lista.

selectExpr([],[]).
selectExpr([H1|T1], [H2|T2]) :- H1 > 20, H1 < 50, not(isOdd(H1)), H2 is H1, selectExpr(T1, T2).
selectExpr([_|T1], L2) :- selectExpr(T1, L2), !.

% 8. Escreva uma função countShorts :: [String] -> Int, que receba uma lista de palavras 
% e retorne a quantidade de palavras dessa lista que possuem menos de 5 caracteres.

countShorts(L1, QUANT) :-
countShorts1(L1, L2),
length(L2, QUANT).

countShorts1([],[]).
countShorts1([H1|T1], [H2|T2]) :- atom_chars(H1, L), length(L, X), X < 5 ,H2 = H1, countShorts1(T1, T2), !.
countShorts1([_|T1], L2) :- countShorts1(T1, L2), !.

% 9. Escreva uma função calcExpr :: [Float] -> [Float], 
% que calcule x^2/2 para cada elemento x da lista de entrada e selecione apenas os resultados que forem maiores que 10.

calcExpr(L1, L2) :-
calculaExpressao(L1, Laux),
selecionagt10(Laux, L2), !.

selecionagt10([],[]).
selecionagt10([H1|T1], [H2|T2]) :- H1 > 10, H2 is H1, selecionagt10(T1, T2).
selecionagt10([_|T1], L2) :- selecionagt10(T1, L2), !.


calculaExpressao([],[]).
calculaExpressao([H1|T1], [H2|T2]) :-
H2 is ( (H1 * H1) / 2 ),
calculaExpressao(T1,T2), !.

% 10. Escreva uma função trSpaces :: String -> String,
% que receba uma string e converta espaços (' ') em traços ('-').

trSpaces(S1, S2) :-
string_chars(S1, L1),
trSpaces1(L1, L2),
string_chars(S2, L2), !.

trSpaces1([],[]).
trSpaces1([H1|T1], [H2|T2]) :- H1 \= ' ', H2 = H1, trSpaces1(T1, T2).
trSpaces1([_|T1], L2) :- trSpaces1(T1, L2).

% 12. Defina uma função selectSnd :: [(Int,Int)] -> [Int], 
% que receba uma lista de tuplas e selecione somente os segundos elementos dessas tuplas, produzindo outra lista.

selectSnd([],[]).
selectSnd(LT, L) :-
LT = [[_|SND]|T1],
L = [H2|T2],
H2 is SND,
selectSnd(T1, T2), !.

% 13. Escreva uma função dotProd :: [Int] -> [Int] -> Int que calcule o 
% somatório dos produtos dos pares de elementos de duas listas.

dotProd(L1, L2, X) :-
prodList(L1, L2, L3),
sum_list(L3, X), !.

prodList([],[],[]).
prodList([H1|T1], [H2|T2], [H3|T3]) :-
H3 is H1 * H2,
prodList(T1,T2, T3), !.

% 15. Em Haskell, a função zipWith é uma função de alta ordem que aplica uma função a pares de elementos de duas listas. 
% Pesquise sobre a função zipWith e utilize-a para definir uma função dotProd', que produza o mesmo resultado da função dotProd do exercício 13'.

zipWith(_, [], [],[]).
zipWith(OP, [H1|T1], [H2|T2], [H3|T3]) :-
call(OP, H1, H2, H3),
zipWith(OP, T1, T2, T3), !.

% EXEMPLO DE USO : zipWith(plus, [1,2,3], [4,5,6], R)
