/*
zrobione = 
1. Wczytywanie danych w formacie tekstowym (dane oddzielone spacj�, tabulatorem, �rednikiem; w pliku same dane bez liczby wierszy lub kolumn; dane mog� by� tekstowe lub numeryczne), mog� posiada� wiersz nag��wkowy z nazwami kolumn. Ka�d� lini� zaczynaj�c� si� od znaku # nale�y zignorowa� (komentarz).
2. Dla ch�tnych wczytywanie danych z Excela lub bezpo�rednie przeklejanie danych z Excela do arkusza z danymi w programie.

Dyskretyzacja
zrobione = 1. Dyskretyzacja zmiennych rzeczywistych na okre�lon� przez u�ytkownika liczb� przedzia��w - dodanie do zbioru danych nowego atrybutu o warto�ciach nominalnych, przyjmuj�cego dla poszczeg�lnych obserwacji warto�� odpowiadaj�c� numerowi przedzia�u przypisanego warto�ci wybranego atrybutu a, przy podziale warto�ci atrybutu a na zadan� liczb� n przedzia��w o r�wnej d�ugo�ci.
zrobione = 2. Zamiana danych tekstowych na numeryczne (np. klasa1, klasa2, klasa3 zmieniane na kolejne liczby ca�kowite 1,2,3 - np. wg kolejno�ci alfabetycznej lub kolejno�ci wyst�pienia)
zrobione = 3. Preferowanie najliczniejszych klas - stosowane do atrybut�w o warto�ciach nominalnych, dodanie do zbioru danych nowego atrybutu o warto�ciach nominalnych przypisuj�cego najliczniej reprezentowanej warto�ci - warto�� 1, drugiej pod wzgl�dem liczno�ci warto�ci - warto�� 2, itd. a� do zadanej liczby n, pozosta�ym warto�ciom - warto�� n+1

Standaryzacja/normalizacja:
1. Normalizacja zmiennych rzeczywistych ( (warto��-�rednia)/odchylenie_standardowe)
2. Zmiana warto�ci z przedzia�u min-max na przedzia�, dla kt�rego warto�ci podaje u�ytkownik - wart1-wart2.

Podstawowe statystyki
1. �rednia
2. Mediana
3. Min/Max
4. Kwartyle (Q1 i Q3)
5. Percentyle (P5, P10, P90, P95)

Wykresy:
1. Wykres rozprosze� 2D - zwyk�a zale�no�� dw�ch zmiennych - u�ytkownik musi mie� mo�liwo�� wyboru zmiennych, mo�liwo�� wy�wietlania klas w kolorach.
2. 3D (dla ch�tnych)
3. Dla ch�tnych wykres 3D z mo�liwo�ci� obrotu.
 */
package swd2014.projekt1.main;
import java.io.FileNotFoundException;
import java.util.LinkedList;

import swd2014.projekt1.csv.CsvFileReader;
import swd2014.projekt1.csv.CsvReadWriteSettings;
import swd2014.projekt1.models.Matrix;
import swd2014.projekt1.utils.Converts;
import swd2014.projekt1.utils.DataPrinting;
import swd2014.projekt1.utils.Statistic;
import swd2014.projekt1.utils.Utils;

public class Main {


	public static void main(String[] args) {
		String file ="plik1.txt";
		boolean hasColumnsNames = false;
		
		CsvFileReader cfr = null;
		Log("\nWczytanie zawarto�ci pliku do macierzy\n");

			cfr = new CsvFileReader(file, new CsvReadWriteSettings(",", true, hasColumnsNames));

		
		if(cfr == null)
			return;
		
		Matrix m = cfr.getData_matrix();
		
		if(hasColumnsNames){
			Log("\nNazwy kolumn ("+m.getnCols()+"):");
	
			LinkedList<String> column_names = cfr.getColumNames();
			for(String cName : column_names)
				Log(cName);
		
		}

		Log("\nWiersze: ("+m.getnRows()+")\n");
		/*
		DataPrinting.printMatrix(m.data, m.getnRows(), m.getnCols());

		Log("\nSortowanie ka�dej kolumny\n");
		for(int i=0; i< m.getnCols(); i++){
			String col[]= m.getColumn(i);
			Arrays.sort(col);
			DataPrinting.printVector(col);
		}
		
		Log("\n?Dyskretyzacja?\n");
		int[] disc_tab = Utils.discretization(3, m.getnRows());
		String[] s_tab = new String[m.getnRows()];
		
		int i=0;
		for(int item : disc_tab)
			s_tab[i++] = String.valueOf(item);
		
		DataPrinting.printVector(disc_tab);
		m.appendColumn(s_tab);
		
		
		Log("\nMacierz po dodaniu dyskretyzacji\n");
		DataPrinting.printMatrix(m.data, m.getnRows(),m.getnCols());
		
		
		Log("\nZamiana warto��i tekstowych na liczbowe\n");		
		int[] n_data = Converts.convertStringData(m.getColumn(4), true);
		String[] n_data_strings = Converts.convertToString(n_data);
		m.replaceColumn(n_data_strings, 4);
		DataPrinting.printMatrix(m.data, m.getnRows(),m.getnCols());
		
*/
		

		
		DataPrinting.printMatrix(m);
		Log("\nPreferowanie najliczniejszych klas\n");
		int class_attr[] = Utils.classAttribution(m.getColumn(0),5);
		m.appendColumn(Converts.convertToString(class_attr));
		
		DataPrinting.printMatrix(m);
		
		int c =3;
		Log("\nStatystyka dla kolumny "+c+"\n");
		
		float mean;
		mean = Statistic.mean(Converts.convertToFloat(m.getColumn(c)));
		Log("średnia: "+ mean+"\n");
		
		float variance;
		variance = Statistic.variance(Converts.convertToFloat(m.getColumn(c)));
		Log("wariancja: "+ variance+"\n");
		
		
		float sd;
		sd = Statistic.standardDeviantion(Converts.convertToFloat(m.getColumn(c)));
		Log("odchylenie standardowe: "+ sd+"\n");
		
		
		float median;
		median = Statistic.median(Converts.convertToFloat(m.getColumn(c)));
		Log("mediana: "+ median+"\n");
		
		float q1;
		q1 = Statistic.q1(Converts.convertToFloat(m.getColumn(c)));
		Log("kwartyl Q1: "+ q1+"\n");
		
		float q3;
		q3 = Statistic.q3(Converts.convertToFloat(m.getColumn(c)));
		Log("kwartyl Q3: "+ q3+"\n");
		
		float percentile;
		percentile = (float) Statistic.quantile(Converts.convertToFloat(m.getColumn(c)),5);
		Log("percentyl 5%: "+ percentile+"\n");
		
		
		Log("\nDyskretyzacja 3 kolumny \n");
		int[] disc_tab = Utils.discretization(3, m.getnRows());
		String[] s_tab = new String[m.getnRows()];
		
		int i=0;
		for(int item : disc_tab)
			s_tab[i++] = String.valueOf(item);
		
		DataPrinting.printVector(disc_tab);
		m.appendColumn(s_tab);
		
		
		Log("\nMacierz po dodaniu dyskretyzacji\n");
		DataPrinting.printMatrix(m.data, m.getnRows(),m.getnCols());
				
	}
	
	
	public static void Log(String s){
		System.out.print(s);
	}

	
}
