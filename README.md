# Program pre prácu so základnými maticovými operáciami
(dokumentácia)

## 1. Zadanie
V jazyku Java napíšte program pre jednoduché maticové operácie ako napríklad **násobenie matíc**, **transpozícia** či **výpočet determinantu** tak, aby bolo možné pracovať s rôznymi reprezentáciami matíc, prípadne nové reprezentácie dodatočne pridávať.

## 2.  Štruktúra programu
Program sa skladá zo štyroch tried a jedného rozhrania (interface-u).

Implementovala som dve hlavné reprezentácie matice - pomocou dvojrozmerného pola `double[][]` pre klasické matice (`class MatrixArray`) a pomocou spojového zoznamu (`class MatrixLinkedList`), ktorý umožňuje efektívnu prácu s riedkymi maticami. 

Rozhranie a trieda sú podobné v tom, že môžu definovať metódy a môžu byť použiteľné ako typy premenných a parametrov. Kľúčový rozdiel je však v tom, že rozhrania nemôžu byť instancované. Definujú množinu popisov metód, ale neposkytujú implementáciu pre tieto metódy. Hlavnou výhodou rozhraní je, že ich možno použiť na definovanie spoločného typu pre objekty rôznych tried. Tento fakt som využila v programe tak, že som vytvorila `interface Matrix`, ktorý definuje základné metódy pre maticové operácie a to nasledovné:
- `getRows()` vracia veľkosť jedného riadku;
- `getColumns()` vracia počet stĺpcov matice;
- `getElement(int row, int column)` vracia prvok matice v riadku row a stĺpci column
- `setElement(int row, int column, double value)` nastaví prvok v riadku row a stĺpci column na hodnotu value
- `map(DoubleFunction<Double> f)` umožňuje mapovanie matice, a teda prechádza jednotlivé prvky 

Pre jednotlivé maticové operácie som vytvorila triedu `MatrixOperator`, ktorá definuje metódy:
- `printMatrix(Matrix m)`
- `isSparse(Matrix m)`
- `convertToLinkedListMatrix(MatrixArray m)`
- `createSameTypeMatrix(Matrix m)`
- `fillMatrix()`
- `transpose(Matrix m)`
- `addMatrices(Matrix first, Matrix second)`
- `scalarMultiplication(Matrix m, scalar)`
- `calculateDeterminant(Matrix m, int rowSize)`
- `getCofactor(Matrix m, Matrix temp, int oldRow, int oldColumn, int rowsOfFirstMatrix)`
- `multiplyMatrices(Matrix first, Matrix second)`
- `cellMultiplication(Matrix first, Matrix second, int row, int column)`
- `inverseMatrix(Marix m)`

## 3. Vstupné a výstupné dáta
Pri spustení programu sa v termináli objaví “uvítací” text a možnosti pre jednotlivé operácie, z ktorých si uživateľ zvolí konkrétnu žiadanú možnosť tak, že do konzole napíše číslo možnosti. Následne sa spustí funkcia `fillMatrix()` (jeden alebo dva krát, v závislosti od zvolenej operácie), ktorá sa spýta uživateľa na počet riadkov a počet stĺpcov jeho (prvej) vstupnej matice. Na obe otázky uživateľ odpovedá jedným celým nezáporným číslom.

Ďalšia otázka sa týka hustoty matice. Uživateľ si môže vybrať, či pracuje s riedkou maticou. Ak nevie, či je jeho matica riedka, program jeho maticu uloží v reprezentácii s dvojrozmerným polom a neskôr overí, či sa jedná o riedku maticu. Ak áno, maticu prevedie do reprezentácie spojovým zoznamom pre budúce efektívne zaobchádzanie.

Nakoniec uživateľ zadáva konkrétne prvky matice vo formáte double, postupne pre jednotlivé riadky zľava doprava a jednotlivé stĺpce zhora dole. Po načítaní počtu prvkov, ktoré odpovedajú vopred stanovenej veľkosti matice program vydá výsledok, či už vo forme matice alebo jedného čísla.

## 4. Záver
Myslím si, že vďaka správnemu naplánovaniu štruktúry programu nebol s vypracovaním zadania programu problém. Následné vytváranie tried a metód nebolo komplikované, iba časovo náročné. Bola som prinútená si znova zopakovať algoritmy, ktoré sa pri maticových operáciach používajú, predovšetkým pri výpočte determinantu a prevádzaní matice na jej inverz. 
Nižšie prikladám aj zdroje, z ktorých som čerpala.

## 5. Zdroje
- Sparse Matrix and its representations, 2022, Geeks For Geeks [online]. Dostupné z: https://www.geeksforgeeks.org/sparse-matrix-representation/
- Algebra Practise Problems, Cofactor expansion [online]. Dostupné z: https://www.algebrapracticeproblems.com/cofactor-expansion/
- HLADÍK, Milan, Lineární algebra (nejen) pro informatiky, 2019, MatfyzPress.
- wikipedia
