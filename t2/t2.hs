import Text.Printf

type Point     = (Float,Float)
type Rect      = (Point,Float,Float)
type Circle    = (Point,Float)


-------------------------------------------------------------------------------
-- Paletas
-------------------------------------------------------------------------------

allColorsPalette :: Int -> [(Int,Int,Int)]
allColorsPalette n = [fromHSVtoRGB(fromIntegral(m * h))| m <- [0..fromIntegral(n-1)]]
    where h = div 360 n

fromHSVtoRGB :: Float -> (Int, Int, Int)
fromHSVtoRGB h    --                    R                               G                            B
    | h >= 0   && h < 60   = (255                        , truncate(coeficiente * 255), 0                          )
    | h >= 60  && h < 120  = (truncate(coeficiente * 255), 255                        , 0                          )
    | h >= 120 && h < 180  = (0                          , 255                        , truncate(coeficiente * 255))
    | h >= 180 && h < 240  = (0                          , truncate(coeficiente * 255), 255                        )
    | h >= 240 && h < 300  = (truncate(coeficiente * 255), 0                          , 255                        )
    | h >= 300 && h <= 360 = (255                        , 0                          , truncate(coeficiente * 255))
    where coeficiente = (1 - abs(( floatMod (h/60) 2) - 1 ))

floatMod :: Float -> Float -> Float
floatMod x y = x - (y * (fromIntegral $ truncate (x/y)))

rgbPalette :: Int -> [(Int,Int,Int)]
rgbPalette n = take (n+1) $ cycle [(255,0,0),(0,255,0),(0,0,255)]

greenPalette :: Int -> [(Int,Int,Int)]
greenPalette n = [(0,50+i*6,0) | i <- [0..n] ]

redPalette :: Int -> [(Int,Int,Int)]
redPalette n = [(50+i*6,0,0) | i <- [0..n] ]

bluePalette :: Int -> [(Int,Int,Int)]
bluePalette n = [(0,0,50+i*6) | i <- [0..n] ]


concatList :: [[l]] -> [l]
concatList [] = []
concatList list = head list ++ concatList (tail list)


-------------------------------------------------------------------------------
-- Geração das figuras em suas posições
-------------------------------------------------------------------------------

genRectsCase1 :: Int -> [[Rect]]
genRectsCase1 n  = [[((m*(w+gap), j * (h + gap)),w,h) | m <- [0..fromIntegral (n-1)]] | j <- [0..fromIntegral(n-1)]]
  where (w,h) = (50,50)
        gap = 10
  


genCirclesCase2 :: Int -> [Circle]
genCirclesCase2 n  = [((x + x * sin(m*(1/fromIntegral(div n 2) * pi)) + gap, y + y * cos(m*(1/fromIntegral(div n 2) * pi)) + gap),r) 
                     | m <- [0..fromIntegral (n-1)]]
  where r = 20
        gap = 50
        x = 150 -- X inicial
        y = 150 -- Y inicial
  
genCirclesCase3 :: Int -> [[Circle]]
genCirclesCase3 n  = [[((x+gap*sin(m*(2/3*pi))+x*fromIntegral(mod j tamLinha),y+gap*cos(m*(2/3*pi))+y*fromIntegral(div j tamLinha)), r) 
                     | m <- [0..fromIntegral(2)]] 
                     | j <- [0..fromIntegral(n-1)]]
  where r = 20
        tamLinha = 4
        gap = 15
        x = 75 -- X inicial
        y = 75 -- Y inicial

genCirclesCase4 :: Int -> [[Circle]]
genCirclesCase4 n = [[((x + m*gap, y * j + gap * sin (m*(1/6 * pi))),r) | m <- [0..fromIntegral (n)]] | j <- [1..3]]
  where r = 20
        gap = 30
        x = 75 -- X inicial
        y = 75 -- Y inicial
-------------------------------------------------------------------------------
-- Strings SVG
-------------------------------------------------------------------------------

-- Gera string representando retângulo SVG 
-- dadas coordenadas e dimensoes do retângulo e uma string com atributos de estilo
svgRect :: Rect -> String -> String 
svgRect ((x,y),w,h) style = 
  printf "<rect x='%.3f' y='%.3f' width='%.2f' height='%.2f' style='%s' />\n" x y w h style
-- Gera string representando retângulo SVG 
-- dadas coordenadas e dimensoes do retângulo e uma string com atributos de estilo
svgCircle :: Circle -> String -> String
svgCircle ((x,y),r) style =
  printf "<circle cx='%.3f' cy='%.3f' r='%.2f' style='%s' />\n" x y r style
-- String inicial do SVG
svgBegin :: Float -> Float -> String
svgBegin w h = printf "<svg width='%.2f' height='%.2f' xmlns='http://www.w3.org/2000/svg'>\n" w h 

-- String final do SVG
svgEnd :: String
svgEnd = "</svg>"

-- Gera string com atributos de estilo para uma dada cor
-- Atributo mix-blend-mode permite misturar cores
svgStyle :: (Int,Int,Int) -> String
svgStyle (r,g,b) = printf "fill:rgb(%d,%d,%d); mix-blend-mode: screen;" r g b

-- Gera strings SVG para uma dada lista de figuras e seus atributos de estilo
-- Recebe uma funcao geradora de strings SVG, uma lista de círculos/retângulos e strings de estilo
svgElements :: (a -> String -> String) -> [a] -> [String] -> String
svgElements func elements styles = concat $ zipWith func elements styles

-------------------------------------------------------------------------------
-- Função principal que gera arquivo com imagem SVG
-------------------------------------------------------------------------------

genCase1 :: IO ()
genCase1 = do
  writeFile "case1.svg" $ svgstrs
  where svgstrs = svgBegin w h ++ svgfigs ++ svgEnd
        svgfigs = svgElements svgRect rects (map svgStyle palette)
        rects = concatList(genRectsCase1 nrects)
        palette = greenPalette (nrects*nrects)
        nrects = 6 -- Quadrado x.x de rects
        (w,h) = (1500,500) 

genCase2 :: IO ()
genCase2 = do
  writeFile "case2.svg" $ svgstrs
  where svgstrs = svgBegin w h ++ svgfigs ++ svgEnd
        svgfigs = svgElements svgCircle circles (map svgStyle palette)
        circles = genCirclesCase2 ncircles
        palette = allColorsPalette ncircles
        ncircles = 18 -- x circulos em circulo
        (w,h) = (1500,500) 

genCase3 :: IO ()
genCase3 = do
  writeFile "case3.svg" $ svgstrs
  where svgstrs = svgBegin w h ++ svgfigs ++ svgEnd
        svgfigs = svgElements svgCircle circles (map svgStyle palette)
        circles = concatList(genCirclesCase3 ncircles)
        palette = rgbPalette (3*ncircles)
        ncircles = 10 -- x figuras compostas por 3 circulos
        (w,h) = (1500,500)

genCase4 :: IO ()
genCase4 = do
  writeFile "case4.svg" $ svgstrs
  where svgstrs = svgBegin w h ++ svgfigs ++ svgEnd
        svgfigs = svgElements svgCircle circles (map svgStyle palette)
        circles = concatList(genCirclesCase4 ncircles)
        palette = greenPalette ncircles ++ redPalette ncircles ++ bluePalette ncircles
        ncircles = 30 -- x circulos formando um senoide
        (w,h) = (1500,500)