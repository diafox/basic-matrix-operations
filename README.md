# Program pre prácu so základnými maticovými operáciami
(dokumentácia)

## 1. Zadanie
V jazyku Java napíšte program pre jednoduché maticové operácie ako napríklad **násobenie matíc**, **transpozícia** či **výpočet determinantu** tak, aby bolo možné pracovať s rôznymi reprezentáciami matíc, prípadne nové reprezentácie dodatočne pridávať.

## 2.  Štruktúra programu
Program sa skladá z piatich tried a jedného rozhrania (interface-u).

### 2.1 Interface Matrix
Rozhranie a trieda sú podobné v tom, že môžu definovať metódy a môžu byť použiteľné ako typy premenných a parametrov. Kľúčový rozdiel je však v tom, že rozhrania nemôžu byť instancované. Definujú množinu popisov metód, ale neposkytujú implementáciu pre tieto metódy. Hlavnou výhodou rozhraní je, že ich možno použiť na definovanie spoločného typu pre objekty rôznych tried. Tento fakt som využila v programe tak, že som vytvorila `interface Matrix`, ktorý definuje základné metódy pre maticové operácie a to nasledovné:
- `getRows()` vracia počet riadkov matice;
- `getColumns()` vracia počet stĺpcov matice;
- `getElement(int row, int column)` vracia prvok matice v riadku row a stĺpci column;
- `setElement(int row, int column, double value)` nastaví prvok v riadku row a stĺpci column na hodnotu value;
- `map(DoubleFunction<Double> f)` umožňuje mapovanie matice, a teda prechádza jednotlivé prvky.

### 2.2 Tri druhy reprezentácie matíc
Implementované sú tri hlavné reprezentácie matice:
- pomocou dvojrozmerného pola `double[][]` pre klasické matice (`class MatrixArray`) - obecná a najčastejšie používaná reprezentácia, kde sa všetky prvky ukladajú do dvojrozmerného pola;
- pomocou dvojrozmerného pola `double[][]` pre riedke matice (`class MatrixSparseArray`)[1] - pole obsahuje tri "riadky", na prvom sa vždy ukladá súradnica riadku, na druhom súradnica stĺpca a na treťom hodnota nenulového prvku. Prvky, ktorých hodnota sa rovná nule sa do pola neukladajú, a tým sa šetrí miesto v pamäti a čas pri manipulácii s prvkami.
- pomocou spojového zoznamu (`class MatrixLinkedList`), ktorý umožňuje efektívnu prácu s riedkymi maticami - takisto neukladá nulové prvky. Jednotlivé uzly spojového zoznamu majú atribúty *riadok*, *stĺpec*, *hodnota* a *pointer na ďalší uzol*.

### 2.3 Trieda MatrixOperator
Trieda MatrixOperator je pomocná trieda, ktorá poskytuje rôzne maticové operácie, ale aj vyplnenie matice či prevádzanie matice na inú reprezentáciu. Trieda berie inštanciu java.util.scanner ako parameter konštruktora.
Definované metódy:
- `printMatrix(Matrix m)`
berie inštanciu Matrix ako parameter a vytlačí jej prvky (každý riadok matice na nový riadok) do konzoly s použitím triedy DecimalFormat na formátovanie desatinných čísel.
- `isSparse(Matrix m)`
berie inštanciu Matrix ako parameter a vracia hodnotu true, ak je matica riedka (obsahuje menej alebo rovných 10% nenulových prvkov), inak vracia hodnotu false.
- `convertToLinkedListMatrix(MatrixArray m)`
pomocná trieda, ktorá berie inštanciu MatrixArray ako parameter a vracia novú inštanciu MatrixLinkedList konvertovanú z parametra.
- `convertToSparseArrayMatrix(MatrixArray m, int nonZero)`
pomocná trieda, ktorá berie inštanciu MatrixArray a počet nenulových prvkov ako parameter a vracia novú inštanciu MatrixSparseArray konvertovanú z parametra.
- `createSameTypeMatrix(Matrix m)`
súkromná pomocná trieda, ktorá berie inštanciu Matrix ako parameter a vracia novú inštanciu Matrix rovnakého typu ako je tá v parametri. Používa sa na vytvorenie novej matice s rovnakým typom ako vstupná matice pre operácie, ako je napríklad transpozícia matice.
- `fillMatrix()`
vyzýva uživateľa na vloženie počtu riadkov a stĺpcov matice. Dáva na výber voľbu reprezentácie matice podľa jej hustoty. V prípade, že uživateľ nevie, či je jeho matica riedka, metóda to overí a zvolí správnu reprezentáciu. Ďalej uživateľ vkladá jednotlivé prvky matice zľava doprava, zhora dolu. Vytvorí a vráti novú inštanciu Matrix.
- `transpose(Matrix m)`
berie inštanciu Matrix ako parameter a vracia novú inštanciu Matrix, ktorá je transponovaním vstupnej matice. Používa metódu createSameTypeMatrix na vytvorenie novej matice rovnakého typu ako vstupná matica.
- `addMatrices(Matrix first, Matrix second)`
berie dve matice rovnakej veľkosti inštancie Matrix ako parametre a vráti maticu, ktorá je výsledkom ich sčítania.
- `scalarMultiplication(Matrix m, scalar)`
berie inštanciu Matrix a skalár ako parametre a vracia novú inštanciu Matrix, ktorá je výsledkom násobenia matice skalárom. Využíva lambda výraz, a teda to, že interface Matrix implementuje funkciu `map`.
- `calculateDeterminant(Matrix m, int rowSize)`
ako parameter berie štvorcovú maticu inštancie Matrix a veľkosť riadku a vypočíta jej determinant pomocou rekurzívneho algoritmu a expanzie kofaktorov. Ak sa rowsSize rovná 1, jednoducho vráti jediný prvok v matici, ktorý je determinantom. V opačnom prípade iteruje každý prvok prvého riadku matice a vypočíta jeho kofaktor volaním metódy `getCofactor`. Potom vynásobí kofaktor so zodpovedajúcim prvkom prvého riadku a rekurzívne vypočíta determinant výslednej sub-matice. Metóda potom sčíta tieto hodnoty so striedavými znamienkami, čím získa determinant matice.[2]
- `getCofactor(Matrix m, Matrix temp, int oldRow, int oldColumn, int rowsOfFirstMatrix)`
vypočítava kofaktor prvku v matici. Metóda má päť argumentov: vstupnú matica, temp je výstupná matica pre kofaktor, oldRow a oldColumn sú indexy prvku, pre ktorý sa má vypočítať kofaktor a rowsOfFirstMatrix je počet riadkov vo vstupnej matici.[2]
- `multiplyMatrices(Matrix first, Matrix second)`
berie dve matice inštancie Matrix ako parametre a vráti maticu, ktorá je výsledkom ich násobenia. 
- `cellMultiplication(Matrix first, Matrix second, int row, int column)`
súkromná pomocná metóda používaná v metóde `multiplyMatrices`, ktorá berie dve matice, číslo riadku a stĺpca ako parameter, vynásobí konkrétnu bunku dvoch matíc a vráti výsledok.
- `inverseMatrix(Marix m)`
ako parameter berie štvorcovú maticu inštancie Matrix a vráti jej inverz.

## 3. Vstupné a výstupné dáta
Pri spustení programu sa v termináli objaví “uvítací” text a možnosti pre jednotlivé operácie, z ktorých si uživateľ zvolí konkrétnu žiadanú možnosť tak, že do konzole napíše číslo možnosti. Následne sa spustí funkcia `fillMatrix` (jeden alebo dva krát, v závislosti od zvolenej operácie), ktorá sa spýta uživateľa na počet riadkov a počet stĺpcov jeho (prvej) vstupnej matice. Na obe otázky uživateľ odpovedá jedným celým nezáporným číslom.

Ďalšia otázka sa týka reprezentácie matice. Uživateľ si môže vybrať, ktorú reprezetáciu zvoliť - či už pre všeobecnú maticu alebo jednu z typov riedkych matíc. Ak nevie, či je jeho matica riedka, program jeho maticu uloží v reprezentácii s dvojrozmerným polom a neskôr overí, či sa jedná o riedku maticu. Ak áno, maticu prevedie do reprezentácie spojovým zoznamom pre budúce efektívne zaobchádzanie.

Nakoniec uživateľ zadáva konkrétne prvky matice vo formáte double, postupne pre jednotlivé riadky zľava doprava a jednotlivé stĺpce zhora dole. Po načítaní počtu prvkov, ktoré odpovedajú vopred stanovenej veľkosti matice program vydá výsledok, či už vo forme matice alebo jedného čísla.

Postup sa opakuje v prípade, že bola zvolená možnosť, kde sú potrebné dve matice ako napríklad sčitovanie alebo násobenie.

Uživateľ si tiež môže zvoliť možnosť "Tutorial", kde mu program predvedie jednotlivé operácie a ich fungovanie s rôznymi reprezentáciami.

## 4. Záver
Myslím si, že vďaka správnemu naplánovaniu štruktúry programu nebol s vypracovaním zadania programu problém. Následné vytváranie tried a metód nebolo komplikované, iba časovo náročné. Bola som prinútená si znova zopakovať algoritmy, ktoré sa pri maticových operáciach používajú, predovšetkým pri výpočte determinantu a prevádzaní matice na jej inverz. 
Nižšie prikladám aj zdroje, z ktorých som čerpala.

## 5. Zdroje
- [1] Sparse Matrix and its representations, 2022, Geeks For Geeks [online]. Dostupné z: https://www.geeksforgeeks.org/sparse-matrix-representation/
- [2] Algebra Practise Problems, Cofactor expansion [online]. Dostupné z: https://www.algebrapracticeproblems.com/cofactor-expansion/
- HLADÍK, Milan, Lineární algebra (nejen) pro informatiky, 2019, MatfyzPress.
- wikipedia
