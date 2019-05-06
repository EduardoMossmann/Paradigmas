santaMariaSegunda(bernardo). santaMariaSegunda(pedro).
santaMariaTerca(pedro). santaMariaTerca(bernardo). santaMariaTerca(maria).
santaMariaQuarta(adriano). santaMariaQuarta(maria).
santaMariaQuinta(pedro). santaMariaQuinta(caren). santaMariaQuinta(bia). santaMariaQuinta(bernardo). santaMariaQuinta(maria).


portoAlegreSegunda(caren).
portoAlegreTerca(caren). portoAlegreTerca(henrique). portoAlegreTerca(bia). portoAlegreTerca(alice).
portoAlegreQuarta(pedro). portoAlegreQuarta(caren). portoAlegreQuarta(bia). portoAlegreQuarta(alice). portoAlegreQuarta(bernardo).


apartamentoSegunda(henrique). apartamentoSegunda(bia). apartamentoSegunda(adriano). apartamentoSegunda(alice). apartamentoSegunda(maria).
apartamentoTerca(adriano).
apartamentoQuarta(henrique).
apartamentoQuinta(henrique). apartamentoQuinta(adriano). apartamentoQuinta(alice).

apartamentoSexta(pedro). apartamentoSexta(caren). apartamentoSexta(henrique). apartamentoSexta(bia). apartamentoSexta(adriano). 
apartamentoSexta(alice). apartamentoSexta(bernardo). apartamentoSexta(maria).



%Acesso

podeTerRoubadoArma(X) :- santaMariaQuarta(X); apartamentoQuarta(X); apartamentoQuinta(X).

podeTerRoubadoChave(X) :- santaMariaSegunda(X); portoAlegreTerca(X).

apartamentoNoMomentoDoCrime(X) :- apartamentoQuinta(X); apartamentoSexta(X).

%Motivo

insano(adriano). insano(maria).
namorouAlguemQueAnittaNamorou(caren). namorouAlguemQueAnittaNamorou(alice).
pobre(bia). pobre(bernardo). pobre(pedro). pobre(maria).


insanidade(X) :- insano(X).
ciume(X) :- namorouAlguemQueAnittaNamorou(X).
dinheiro(X) :- pobre(X).

%Final

assassino(X) :- motivo(X), acesso(X), !.

acesso(X) :- podeTerRoubadoArma(X), podeTerRoubadoChave(X), apartamentoNoMomentoDoCrime(X).

motivo(X) :- insanidade(X); ciume(X); dinheiro(X).