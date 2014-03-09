/*
zrobione = 1. Wczytywanie danych w formacie tekstowym (dane oddzielone spacj¹, tabulatorem, œrednikiem; w pliku same dane bez liczby wierszy lub kolumn; dane mog¹ byæ tekstowe lub numeryczne), mog¹ posiadaæ wiersz nag³ówkowy z nazwami kolumn. Ka¿d¹ liniê zaczynaj¹c¹ siê od znaku # nale¿y zignorowaæ (komentarz).
2. Dla chêtnych wczytywanie danych z Excela lub bezpoœrednie przeklejanie danych z Excela do arkusza z danymi w programie.

Dyskretyzacja
zrobione = 1. Dyskretyzacja zmiennych rzeczywistych na okreœlon¹ przez u¿ytkownika liczbê przedzia³ów - dodanie do zbioru danych nowego atrybutu o wartoœciach nominalnych, przyjmuj¹cego dla poszczególnych obserwacji wartoœæ odpowiadaj¹c¹ numerowi przedzia³u przypisanego wartoœci wybranego atrybutu a, przy podziale wartoœci atrybutu a na zadan¹ liczbê n przedzia³ów o równej d³ugoœci.
zrobione = 2. Zamiana danych tekstowych na numeryczne (np. klasa1, klasa2, klasa3 zmieniane na kolejne liczby ca³kowite 1,2,3 - np. wg kolejnoœci alfabetycznej lub kolejnoœci wyst¹pienia)
zrobione = 3. Preferowanie najliczniejszych klas - stosowane do atrybutów o wartoœciach nominalnych, dodanie do zbioru danych nowego atrybutu o wartoœciach nominalnych przypisuj¹cego najliczniej reprezentowanej wartoœci - wartoœæ 1, drugiej pod wzglêdem licznoœci wartoœci - wartoœæ 2, itd. a¿ do zadanej liczby n, pozosta³ym wartoœciom - wartoœæ n+1

Standaryzacja/normalizacja:
1. Normalizacja zmiennych rzeczywistych ( (wartoœæ-œrednia)/odchylenie_standardowe)
2. Zmiana wartoœci z przedzia³u min-max na przedzia³, dla którego wartoœci podaje u¿ytkownik - wart1-wart2.

Podstawowe statystyki
1. Œrednia
2. Mediana
3. Min/Max
4. Kwartyle (Q1 i Q3)
5. Percentyle (P5, P10, P90, P95)

Wykresy:
1. Wykres rozproszeñ 2D - zwyk³a zale¿noœæ dwóch zmiennych - u¿ytkownik musi mieæ mo¿liwoœæ wyboru zmiennych, mo¿liwoœæ wyœwietlania klas w kolorach.
2. 3D (dla chêtnych)
3. Dla chêtnych wykres 3D z mo¿liwoœci¹ obrotu.
 */
package com.example;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;

import utils.Converts;
import utils.DataPrinting;
import utils.Statistic;
import utils.Utils;
import csv.CsvFileReader;
import csv.CsvReadWriteSettings;

public class Main {


	public static void main(String[] args) {
		String file ="plik1.txt";
		boolean hasColumnsNames = false;
		
		CsvFileReader cfr = null;
		Log("\nWczytanie zawartoœci pliku do macierzy\n");
		try {
			cfr = new CsvFileReader(file, new CsvReadWriteSettings(",", true, hasColumnsNames));
 		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
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

		Log("\nSortowanie ka¿dej kolumny\n");
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
		
		
		Log("\nZamiana wartoœæi tekstowych na liczbowe\n");		
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
		Log("œrednia: "+ mean+"\n");
		
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
				
	}
	
	
	public static void Log(String s){
		System.out.print(s);
	}

	
}
