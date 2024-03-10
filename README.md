Clasa Secretariat contine 4 colectii:
1. HashMap<String, Student> studenti
-pentru a avea acces la orice student din colectie in O(1) stiind doar numele lui(valabil in aproapte toate comenzile primite de secretariat)
-pentru a putea adauga studenti in O(1)
-Mi se pare mai practic sa folosec un HashMap in care sa impun eu unicitatea decat un HashSet fiindca imi pot instantia mult mai usor obiecte de tip student stiind doar numele, fara a mai fi nevoie sa parcurg tot HashSet-ul.

2. TreeMap<Student, List<String>> studenti_ierarhie
-pentru o a avea o colectie de studenti ordonata prin constructie(Tree) si pentru a putea obtine direct lista de optiuni asociata fara a folosi alte atribute(Map)
-pentru a asocia in timp constant lista de optini cu studentul (nume -> Student -> optini)
-un dezavantaj este in cazul unei contestatii fiindca trebuie eliminat si inserat din nou studentul, operatiuni in O(log n) fiecare
-pentru lista de optiuni am folosit un ArrayList fiindca nu sunt necesare cautari, inserari sau eliminari dupa creare, ci doar o parcurge secventiala(nu am folosit un ArrayDeque sau LinkedList fiindca nu e necesar sa elimin optiunile verificate)

3/4. HashMap<String, Curs<StudentLicenta>> cursuriLicenta
     HashMap<String, Curs<StudentMaster>> cursuriMaster
-pentru a avea acces la orice curs din colectie in O(1) stiind doar numele lui(valabil in multe cazuri)
-pentru a putea adauga cursuri in O(1)
-a fost nevoie de doua colectii fiindca nu am gasit o solutie pentru a pune la olalata cursuri de licenta si master fara a primi warning-uri in Intellij sau la gradle check

In clasa Curs<T extens Student> am folosit o colectie pentru studentii inrolati:
TreeMap<T> studenti
-pentru o a avea o colectie de studenti ordonata prin constructie
-clasa Curs are un atribut pentru ultima medie din timpul repartizarii, asa ca in caz de egalitate pe ultimul loc nu mai e nevoie de parcuregea intregului arbore pentru verificare