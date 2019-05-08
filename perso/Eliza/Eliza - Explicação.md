## Explicação do código - Eliza

## Explicação Geral - Regras

O bot Eliza funciona em alguns passos: ele lê uma frase sua e separa em strings.

Eliza tem um "banco de dados" de regras baseado em **palavras-chave** e a **importância** sobre as outras.

Com a lista de strings, comparamos cada palavra com as palavras-chave definidas pelo programa, e avaliamos
conforme a sua **importância**. A palavra-chave encontrada na frase com maior relevância sobre as outras é escolhida
e uma resposta baseada nesta palavra é obtida.


##### Simplification Rules

Também temos as **simplification rules**, onde palavras que não são exatamente iguais as palavras-chaves podem ser entendidas como uma
Por exemplo: Do not é entendido como Dont pelo programa. Machines, computers e machine são subentendidos como computer pelo programa.

##### Palavras complementares à palavra-chave (keyword)

Com a palavra-chave de maior relevância encontrada, procuramos por outras **palavras que completam** o contexto da frase inserida.
Por exemplo: a palavra-chave "Remember" é dividida em 2 categorias de **palavras complementares**:
[Do, i, remember] ou [you, remember]. 
Caso remember não esteja acompanhado de [you] ou [do, i] na frase, Eliza procura outras keyword(Palavra-chave).

As palavras complementares são enumeradas, sendo a escolha baseada em qual conjunto de palavras o programa encontrar primeiro.
Caso o programa não ache nenhuma, ou ele responde para um caso geral, ou retorna a procura de outra palavra-chave.

##### Resposta do programa

Com a palavra-chave encontrada, o conjunto de palavras complementares escolhido, o programa nos responde com uma **frase pré-definida**.

Existem várias combinações de frases pré-definidas, que são escolhidas linearmente, conforme a escolha daquela palavra-chave com aquelas palavras complementares ocorre, por isso o programa guarda a last response used(Ultima resposta usada).

##### Exemplificação
Ao escrevermos: Can you help me with my computer?
As palavras-chave com valor são:
Computer - 50
Can - 0
My - 0
Logo, a maior relevância é de Computer, com 50 pontos.

Computer é uma regra que não utiliza **palavras complementares**, então ele só escreve a resposta -> [do,computers,worry,you,?]-> "Do computers worry you?".
Caso a regra computer seja acessada novamente, o programa responderá -> [why,do,you,mention,computers,?] -> "Why do you mention computers?",
e assim por diante.

### Modificação do código

Inseri a seguinte regra para Eliza com prioridade 101 (alta) apenas para demonstração:

rules([[paradigmas, 101],[
	[1,[_,best,class,Y],0,
		[paradigmas, is, the, best, class,.]],
	[2,[_,is, boring, Y],0,
		[paradigmas, is, not, boring, ,, it, is, awesome,.]],
	[3,[_],0,
		[paradigmas, becomes, even, better, gaining, extra, points,.]]]]).


E obtive como resposta, nas seguintes consultas:


Paradigmas is the best class?
> Paradigmas is the best class.

No, i think paradigmas is boring.
> Paradigmas is not boring, it is awesome.

Do you like paradigmas, so?
> Paradigmas becomes even better gaining extra points.