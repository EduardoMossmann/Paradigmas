-- Personalizada que transforma cores de HSV em RGB
-- a partir de N circulos. Também foi implementado no meu t2.


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
