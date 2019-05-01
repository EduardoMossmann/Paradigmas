
odd(N) :- X is mod(N,2),
X = 1.



hasN([], 0).
hasN(L, N) :-
N > 0,
L = [_ | T],
N1 is N - 1,
hasN(T , N1), !.



inc99([], []).
inc99(L1, L2) :-
L1 = [H1|T1],
L2 = [H2|T2],
H2 is H1 + 99,
inc99(T1,T2), !.



incN([], [], _).
incN(L1, L2, N) :-
L1 = [H1|T1],
L2 = [H2|T2],
H2 is H1 + N,
incN(T1,T2, N), !.



comment([], []).
comment(L1, L2) :-
L1 = [H1 | T1],
L2 = [H2 | T2],
atomic_concat("%%", H1, X),
string_concat("", X, H2),
comment(T1, T2), !.



onlyEven([],[]).
onlyEven([H1 | T1],[H2 | T2]) :- odd(H1), H2 is H1, onlyEven(T1, T2), !.
onlyEven([_ | T1],L2) :- onlyEven(T1, L2), !.



countdown(0, []).
countdown(N, L) :-
L = [H|T],
N > 0,
H is N,
N1 is N - 1,
countdown(N1, T), !.



nRandoms(0, []).
nRandoms(N, L) :-
L = [H|T],
N > 0,
random(0, 100, H),
X is N - 1,
nRandoms(X, T), !.



pow(_,0,1).
pow(N,E,X) :- 
E1 is E - 1,
pow(N,E1,X1),
X is X1*N, !.



potN0(0, []).
potN0(N, L) :-
L = [H|T],
N1 is N - 1,
pow(2, N1, H),
potN0(N1, T), !.



zipmult([],[],[]).
zipmult(L1, L2, R) :-
L1 = [H1|T1],
L2 = [H2|T2],
R  = [H3|T3],
H3 is H1 * H2,
zipmult(T1, T2, T3), !.



potencia(0, []).
potencia(N, L) :-
aux(N, N, L).

aux(0, _, []).
aux(N, Nconstante, L) :-
L = [H|T],
E is Nconstante - N,
pow(2, E, H),
N1 is N - 1,
aux(N1, Nconstante, T), !.




cedulas(0,[],[]).
cedulas(V,L1,L2) :-
L1 = [H1|T1],
L2 = [H2|T2],
H2 is div(V, H1),
V1 is V - (H1*H2),
cedulas(V1, T1, T2), !.





