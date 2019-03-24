import Data.Char

--1) Crie uma função isVowel :: Char -> Bool que verifique se um caracter é uma vogal ou não.

isVowel :: Char -> Bool
isVowel c = elem c "aeiouAEIOU"

--2) Escreva uma função addComma, que adicione uma vírgula no final de cada string contida numa lista.

addComma :: [String] -> [String]
addComma str = map ( ++ ",") str

--3) Crie uma função htmlListItems :: [String] -> [String], que receba uma lista de strings e retorne outra lista contendo as strings formatadas como itens de lista em HTML.
--Resolva este exercício COM e SEM funções anônimas (lambda). Exemplo de uso da função:

concatenaHtml :: String -> String
concatenaHtml str = "<LI>" ++ str ++ "</LI>"

htmlListItems :: [String] -> [String]
htmlListItems str = map (concatenaHtml) str

htmlListItemsLambda :: [String] -> [String] 
htmlListItemsLambda str = map (\n -> "<LI>" ++ n ++ "</LI>") str


--4) Defina uma função que receba uma string e produza outra retirando as vogais, conforme os exemplos abaixo.

semVogais :: String -> String
semVogais str = filter (\n -> isVowel n /= True) str

semVogaisLambda :: String -> String
semVogaisLambda str = filter (\n -> notElem n "aeiouAEIOU") str

--5) Defina uma função que receba uma string, possivelmente contendo espaços, e que retorne outra string substituindo os demais caracteres por '-', mas mantendo os espaços.

codificaAux :: Char -> Char
codificaAux c = if c /= ' ' then '-' else c

codifica :: String -> String
codifica str = map (codificaAux) str

codificaLambda :: String -> String
codificaLambda str = map (\n ->  if n /= ' ' then '-' else n) str

--6) Escreva uma função firstName :: String -> String que, dado o nome completo de uma pessoa, obtenha seu primeiro nome. 
--Suponha que cada parte do nome seja separada por um espaço e que não existam espaços no início ou fim do nome. 

firstName :: String -> String
firstName str = takeWhile (/= ' ') str

--7) Escreva uma função isInt :: String -> Bool que verifique se uma dada string só contém dígitos de 0 a 9.

isInt :: String -> Bool
isInt str = if length str > length (filter (`elem` "0123456789") str) then False else True

--8) Escreva uma função lastName :: String -> String que, dado o nome completo de uma pessoa, obtenha seu último sobrenome.
--Suponha que cada parte do nome seja separada por um espaço e que não existam espaços no início ou fim do nome.

lastName :: String -> String
lastName str = last (words str)

--9) Escreva uma função userName :: String -> String que, dado o nome completo de uma pessoa,
--crie um nome de usuário (login) da pessoa, formado por: primeira letra do nome seguida do sobrenome, tudo em minúsculas.

userName :: String -> String
userName str = map toLower (concat [take 1 (head (words str)), last (words str)])

--10) Escreva uma função encodeName :: String -> String que substitua vogais em uma string,
--conforme o esquema a seguir: a = 4, e = 3, i = 2, o = 1, u = 0.

eNameAux :: Char -> Char
eNameAux x = if (x == 'a') || (x == 'A') then '4' else
             if (x == 'e') || (x == 'E') then '3' else
             if (x == 'i') || (x == 'I') then '2' else
             if (x == 'o') || (x == 'O') then '1' else
             if (x == 'u') || (x == 'U') then '0' else x

encodeName :: String -> String
encodeName str = map (\n -> eNameAux n) str

--11) Escreva uma função betterEncodeName :: String -> String
--que substitua vogais em uma string, conforme este esquema: a = 4, e = 3, i = 1, o = 0, u = 00.

bEncodeNameAux :: Char -> String
bEncodeNameAux x = if (x == 'a') || (x == 'A') then "4" else
                   if (x == 'e') || (x == 'E') then "3" else
                   if (x == 'i') || (x == 'I') then "1" else
                   if (x == 'o') || (x == 'O') then "0" else
                   if (x == 'u') || (x == 'U') then "00" else [x]

betterEncodeName :: String -> String
betterEncodeName str = concatMap (\n -> bEncodeNameAux n) str

--12) Dada uma lista de strings, produzir outra lista com strings de 10 caracteres,
--usando o seguinte esquema: strings de entrada com maiss de 10 caracteres são truncadas,
--strings com até 10 caracteres são comp,letadas com '.' até ficarem com 10 caracteres.

dezChar :: [String] -> [String]
dezChar x = map (\n -> if length n >= 10 then take 10 n else take 10 (concat [n, ".........."])) x