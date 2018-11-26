/*
Programa de A estrella con N Reinas en tablero de ajedrés
Autor:      Luis Fernando Pinillos Gómez
Fecha:      Junio de 2018
 */
package reinasdeajedrez;
import java.util.Scanner;
import java.util.Vector;
import java.util.Random;
import java.util.stream.IntStream;

public class ReinasDeAjedrez {
    
    static final int MAX_REINAS = 5;
    static final int MAX_CICLOS = 4000;
    static final int MAX_FILAS = 5;
    static final int MAX_COLUMNAS = 5;
    
    public static class Nodo{
        int nTab[][] = new int[MAX_FILAS][MAX_COLUMNAS];   //Definición del Estado
        int nNumRei;                    //Definición del Estado
        
        int nOpe;       //Variable operativa
        float fFunEva;  //Variable operativa
        Nodo pnNod;     //Variable de navegación
        Nodo pnSig;     //Variable de navegación
        Nodo pnAnt;     //Variable de navegación
        Nodo pnPad;     //Variable de navegación
        
        public Nodo(){  
            //Valores del Estado            
            for(int nCon1=0; nCon1<MAX_FILAS; nCon1++){
                for(int nCon2=0; nCon2<MAX_COLUMNAS; nCon2++){
                    this.nTab[nCon1][nCon2]=0;
                }
            }
            //Variables Operativas y Navegación
            this.nOpe = 0;
            if((this.nTab[0][0] != 0)||(this.nTab[0][MAX_COLUMNAS-1] != 0)||(this.nTab[MAX_FILAS-1][0] != 0)||(this.nTab[MAX_FILAS-1][MAX_COLUMNAS-1] != 0)){
                this.fFunEva=100;
            }else{
                this.fFunEva=10;
            } 
            //this.fFunEva=4;
            this.pnSig = this.pnAnt = this.pnPad = null;
            this.pnNod = this;
        }

        public Nodo(int nFil, int nCol){  
            //Valores del Estado            
            for(int nCon1=0; nCon1<MAX_FILAS; nCon1++){
                for(int nCon2=0; nCon2<MAX_COLUMNAS; nCon2++){
                    this.nTab[nCon1][nCon2]=0;
                }
            }
            for(int nCon1=0; nCon1<MAX_REINAS; nCon1++){
                this.nTab[nFil][nCon1]=MAX_REINAS+1;
                this.nTab[nCon1][nCol]=MAX_REINAS+1;
                if(((nFil+nCon1)<MAX_REINAS)&&((nCol+nCon1)<MAX_REINAS)){
                    this.nTab[nFil+nCon1][nCol+nCon1]=MAX_REINAS+1;
                }
                if(((nFil+nCon1)<MAX_REINAS)&&((nCol-nCon1)>-1)){
                    this.nTab[nFil+nCon1][nCol-nCon1]=MAX_REINAS+1;
                }
                if(((nFil-nCon1)>-1)&&((nCol+nCon1)<MAX_REINAS)){
                    this.nTab[nFil-nCon1][nCol+nCon1]=MAX_REINAS+1;
                }
                if(((nFil-nCon1)>-1)&&((nCol-nCon1)>-1)){
                    this.nTab[nFil-nCon1][nCol-nCon1]=MAX_REINAS+1;
                }                                        
            }
            this.nTab[nFil][nCol]=1;
            this.nNumRei = 1; 
            //Variables Operativas y Navegación
            this.nOpe = 0;
            if((this.nTab[0][0] != 0)||(this.nTab[0][MAX_COLUMNAS-1] != 0)||(this.nTab[MAX_FILAS-1][0] != 0)||(this.nTab[MAX_FILAS-1][MAX_COLUMNAS-1] != 0)){
                this.fFunEva=100;
            }else{
                this.fFunEva=10;
            } 
            this.pnSig = this.pnAnt = this.pnPad = null;
            this.pnNod = this;
        }                
        
        public void showNodo(){ //Método show
            for(int nCon1=0; nCon1<MAX_FILAS; nCon1++){
                for(int nCon2=0; nCon2<MAX_COLUMNAS; nCon2++){
                    System.out.print(this.nTab[nCon1][nCon2]+" ");
                }
                System.out.println(" ");
            }
        }

        public boolean igual(Nodo pnNodTem){            
            boolean bVerCic;
            boolean bVer = (this.nNumRei==pnNodTem.nNumRei);
            if(bVer == false){
                return false;
            }else{
                bVer = true;
                for(int nCon1=0; nCon1<MAX_FILAS; nCon1++){
                    for(int nCon2=0; nCon2<MAX_COLUMNAS; nCon2++){
                        if(this.nTab[nCon1][nCon2] == pnNodTem.nTab[nCon1][nCon2]){
                            bVerCic = true;
                        }else{
                            bVerCic = false;
                        }
                        bVer = bVer && bVerCic; 
                    }
                }
                return bVer;
            }            
        }

        public Nodo posicionReina(int nFil, int nCol){
            //Imagen del Estado
            int nTemTab[][] = new int[MAX_FILAS][MAX_COLUMNAS];
            int nTemNumRei;            
            for(int nCon1=0; nCon1<MAX_FILAS; nCon1++){
                for(int nCon2=0; nCon2<MAX_COLUMNAS; nCon2++){
                    nTemTab[nCon1][nCon2]=this.nTab[nCon1][nCon2];
                }
            }
            nTemNumRei = this.nNumRei;
            
            //Se crea el nodo nuevo como si fuera posible
            if(nTemTab[nFil][nCol]==0){  
                for(int nCon1=0; nCon1<MAX_REINAS; nCon1++){
                    nTemTab[nFil][nCon1]=MAX_REINAS+1;
                    nTemTab[nCon1][nCol]=MAX_REINAS+1;
                    if(((nFil+nCon1)<MAX_REINAS)&&((nCol+nCon1)<MAX_REINAS)){
                        nTemTab[nFil+nCon1][nCol+nCon1]=MAX_REINAS+1;
                    }
                    if(((nFil+nCon1)<MAX_REINAS)&&((nCol-nCon1)>-1)){
                        nTemTab[nFil+nCon1][nCol-nCon1]=MAX_REINAS+1;
                    }
                    if(((nFil-nCon1)>-1)&&((nCol+nCon1)<MAX_REINAS)){
                        nTemTab[nFil-nCon1][nCol+nCon1]=MAX_REINAS+1;
                    }
                    if(((nFil-nCon1)>-1)&&((nCol-nCon1)>-1)){
                        nTemTab[nFil-nCon1][nCol-nCon1]=MAX_REINAS+1;
                    }                                        
                }
                nTemNumRei++;
                nTemTab[nFil][nCol]=nTemNumRei;              
            }
            

            
            //Condición general si no es posible se recargan los valores
            if((this.nTab[nFil][nCol]) != 0){
                for(int nCon1=0; nCon1<MAX_FILAS; nCon1++){
                    for(int nCon2=0; nCon2<MAX_COLUMNAS; nCon2++){
                        nTemTab[nCon1][nCon2]=this.nTab[nCon1][nCon2];
                    }
                }
                nTemNumRei = this.nNumRei;
            }
            
            //Creo el nodo nuevo
            Nodo NodNue = new Nodo();
            for(int nCon1=0; nCon1<MAX_FILAS; nCon1++){
                for(int nCon2=0; nCon2<MAX_COLUMNAS; nCon2++){
                    NodNue.nTab[nCon1][nCon2]=nTemTab[nCon1][nCon2];
                }
            }            
            NodNue.nNumRei=nTemNumRei;
            NodNue.nOpe = nFil*MAX_REINAS+nCol;
            if((NodNue.nTab[0][0] == 0||(NodNue.nTab[0][MAX_COLUMNAS-1] == 0)||(NodNue.nTab[MAX_FILAS-1][0] == 0)||(NodNue.nTab[MAX_FILAS-1][MAX_COLUMNAS-1] == 0))){ //
                if((NodNue.nTab[0][0] == 9||(NodNue.nTab[0][MAX_COLUMNAS-1] == 9)||(NodNue.nTab[MAX_FILAS-1][0] == 9)||(NodNue.nTab[MAX_FILAS-1][MAX_COLUMNAS-1] == 9))){
                    NodNue.fFunEva=10;
                }else{
                    NodNue.fFunEva=10;
                }                
            }else{
                NodNue.fFunEva=100;
            } 
            //System.out.println("fFunEva del nodo nuevo: "+NodNue.fFunEva);//Para ver
            NodNue.pnPad = this.pnNod;
            return NodNue;
        }
        
        public void moverContenido(Nodo pnNodAux){            
            for(int nCon1=0; nCon1<MAX_FILAS; nCon1++){
                for(int nCon2=0; nCon2<MAX_COLUMNAS; nCon2++){
                    pnNodAux.nTab[nCon1][nCon2]=this.nTab[nCon1][nCon2];
                }
            }
            pnNodAux.nNumRei = this.nNumRei;

            pnNodAux.nOpe = this.nOpe;
            pnNodAux.fFunEva = this.fFunEva;
            pnNodAux.pnPad = this.pnPad;
        }

        public Vector buscarSolucion(){            
            Nodo NodBus;
            Vector nOpeSol = new Vector(1,1);
            NodBus = this.pnNod;            
            do{
                nOpeSol.insertElementAt(NodBus.nOpe, 0);
                System.out.print(nOpeSol.get(0)+" ");
                NodBus = NodBus.pnPad;            
            }while(NodBus.nOpe != 0); 
            System.out.println("");
            return nOpeSol;            
        }      
    }
    
    public static class Lista extends Nodo{
        Nodo pnIni, pnFin;
        Nodo pnNod;
    
        Lista(){
            Nodo pnIni=null;
            Nodo pnFin=null;
            Nodo pnNod=null;
        }
   
        public void insertar(Nodo pnNodNue){
            if(pnIni==null){
                pnIni=pnFin=pnNod=pnNodNue;                
                pnNodNue.pnSig = null;
                pnNodNue.pnAnt = null;
                //pnNodNue.pnPad = null;                
            }else{
                pnNodNue.pnSig = pnIni;
                pnIni.pnAnt = pnNodNue;
                pnIni = pnNodNue;
                pnIni.pnAnt = null;                
            }
        }
        
        public void poner(Nodo pnNodNue){            
            if(pnFin==null){
                pnIni=pnNodNue;
                pnNodNue.pnSig = null;
                pnNodNue.pnAnt = null;
                //pnNodNue.pnPad = null;
                pnFin=pnNod=pnNodNue;
            }else{
                pnFin.pnSig=pnNodNue;
                pnNodNue.pnAnt = pnFin;
                pnFin=pnNodNue;
                pnNodNue.pnSig=null; 
            }
        } 
        
        public void moverPrimerNodo(Lista LisDes, int nPolUbi){
            if(this.pnIni==null){   //No hay nodo en Abiertos
                return;
            }
            if(nPolUbi==0){ //Política FIFO
                if((this.pnIni.pnSig==null)&&(LisDes.pnIni==null)){ //Abiertos 1 nodo y Cerrados 0 nodos
                    LisDes.pnIni = this.pnIni;
                    LisDes.pnFin = this.pnFin;
                    this.pnIni = this.pnFin = null;
                }else{
                    if((this.pnIni.pnSig == null)&&(LisDes.pnIni != null)){ //Abiertos 1 nodo y Cerrados 1 o mas nodos
                        this.pnIni.pnAnt = LisDes.pnFin;
                        LisDes.pnFin.pnSig = this.pnIni;
                        LisDes.pnFin = this.pnIni;
                        this.pnIni = this.pnFin = null;
                    }else{
                        if((this.pnIni.pnSig != null)&&(LisDes.pnIni != null)){ //Abiertos 2 o mas nodos y Cerrados 1 o más nodos
                            this.pnNod = this.pnIni.pnSig;
                            this.pnIni.pnAnt = LisDes.pnFin;
                            LisDes.pnFin.pnSig = this.pnIni;
                            LisDes.pnFin = this.pnIni;
                            this.pnIni = this.pnNod;
                        }
                    }                
                }
                                
            }else{          //Política LIFO
                if((this.pnIni.pnSig==null)&&(LisDes.pnIni==null)){ //Abiertos 1 nodo y Cerrados 0 nodos
                    LisDes.pnIni = this.pnIni;
                    LisDes.pnFin = this.pnFin;
                    this.pnIni = this.pnFin = null;
                }else{
                    if((this.pnIni.pnSig == null)&&(LisDes.pnIni != null)){ //Abiertos 1 nodo y Cerrados 1 o mas nodos
                        this.pnIni.pnSig = LisDes.pnIni;
                        LisDes.pnIni.pnAnt = this.pnIni;
                        LisDes.pnIni = this.pnIni;
                        this.pnIni = this.pnFin = null;
                    }else{
                        if((this.pnIni.pnSig != null)&&(LisDes.pnIni != null)){ //Abiertos 2 o mas nodos y Cerrados 1 o más nodos
                            this.pnNod = this.pnIni.pnSig;
                            this.pnIni.pnSig = LisDes.pnIni;
                            LisDes.pnIni.pnAnt = this.pnIni;
                            LisDes.pnIni = this.pnIni;
                            this.pnIni = this.pnNod;
                        }       
                    }
                }                         
            }
        }
        
        public void eliminarNodoDeLista(Lista LisVer){             
            this.pnNod=this.pnIni;            
            while(this.pnNod != null){                
                LisVer.pnNod = LisVer.pnIni;                
                while(LisVer.pnNod != null){                    
                    if(LisVer.pnNod.igual(this.pnNod)){          //Los nodos son iguales                                                
                        if(this.pnNod == this.pnIni){           //Eliminar Nodo inicial
                            this.pnIni = this.pnIni.pnSig;
                            //this.pnNod = this.pnIni;
                            if(this.pnIni != null){
                                this.pnIni.pnAnt = null;
                            }
                            //this = this.pnIni;
                        }else{
                            if(this.pnNod == this.pnFin){       //Eliminar Nodo Final
                                this.pnFin = this.pnFin.pnAnt;
                                this.pnFin.pnSig = null;
                                this.pnNod = this.pnFin; 
                            }else{                              //Eliminar Nodos Intermedio
                                this.pnNod.pnAnt.pnSig=this.pnNod.pnSig;
                                this.pnNod.pnAnt.pnSig.pnAnt = this.pnNod.pnAnt;
                                this.pnNod = this.pnNod.pnAnt;
                            }
                        }
                    }else{
                        //System.out.println("No son iguales");                        
                    }                    
                    LisVer.pnNod=LisVer.pnNod.pnSig;
                }
                this.pnNod = this.pnNod.pnSig;
            }
        }
        
        public void unirLista(Lista LisTem, int nPolUbi){
            if(nPolUbi == 0){   //Política FIFO
                if(LisTem.pnIni==null){     //Cuando Generados está vacía
                    return;
                }else{
                    if(this.pnIni==null){   //Cuando Abiertos está vacia
                        this.pnIni = LisTem.pnIni;
                        this.pnFin = LisTem.pnFin;
                        LisTem.pnIni = LisTem.pnFin = null;
                    }else{                  //Cuando ambas tienen nodos
                        this.pnFin.pnSig = LisTem.pnIni;
                        LisTem.pnIni.pnAnt = this.pnFin;
                        this.pnFin = LisTem.pnFin;
                        LisTem.pnIni = LisTem.pnFin = null;
                    }
                }                
            }else{              //Política LIFO
                if(LisTem.pnIni==null){     //Cuando Abiertos está vacia
                    return;
                }else{
                    if(this.pnIni==null){   //Cuando Generados está vacía
                        this.pnIni = LisTem.pnIni;
                        this.pnFin = LisTem.pnFin;
                        LisTem.pnIni = LisTem.pnFin = null;
                    }else{                  //Cuando ambas tienen nodos
                        LisTem.pnFin.pnSig = this.pnIni;
                        this.pnIni.pnAnt = LisTem.pnFin;
                        this.pnIni = LisTem.pnIni;
                        LisTem.pnIni = LisTem.pnFin = null;
                    }
                }
            }
        }
        
        public void ordenarLista(){
            Nodo NodSig;
            Nodo NodTem = new Nodo();
            int nCon=0;            
            if(this.pnIni == null){ //Caso I: La lista está vacia                
                return;
            }else{                
                if(this.pnIni.pnSig == null){ //Caso II: Existe un solo nodo                    
                    return;
                }else{  //Existen al menos dos nodos                    
                    this.pnNod = this.pnIni;                                        
                    do{
                        NodSig = this.pnNod.pnSig;                        
                        do{
                            //System.out.println("fFunEva: "+this.pnNod.fFunEva+" "+NodSig.fFunEva);//Para ver
                            if(this.pnNod.fFunEva > NodSig.fFunEva){                                  
                                this.pnNod.moverContenido(NodTem);
                                NodSig.moverContenido(this.pnNod);
                                NodTem.moverContenido(NodSig);
                            }
                            NodSig = NodSig.pnSig;
                        }while(NodSig != null);
                        this.pnNod = this.pnNod.pnSig;
                    }while(this.pnNod.pnSig != null);                    
                }
            }
        }
        
        public void showLista(){
            Nodo pnSeg;
            pnSeg = pnIni;
            if(pnIni==null){
                System.out.print("Lista vacia");
            }
            while (pnSeg != null){
                for(int nCon1=0; nCon1<MAX_FILAS; nCon1++){
                    for(int nCon2=0; nCon2<MAX_COLUMNAS; nCon2++){
                        System.out.print(pnSeg.nTab[nCon1][nCon2]+" ");
                    }
                    System.out.println();
                }
                pnSeg = pnSeg.pnSig; 
                System.out.println("");
            }
            System.out.println("");
        }  
        
        public int longitudLista(){
            int nNumNod=0;
            Nodo pnSeg;
            pnSeg = pnIni;
            if(pnIni==null){
                nNumNod=0;
            }
            while (pnSeg != null){
                nNumNod ++;
                pnSeg = pnSeg.pnSig; 
            }
            return nNumNod;            
        }
    }

    public static void interpreteSolucion(int nOpeSol){
        switch (nOpeSol){
            case 0:
                System.out.println("Origen");
                break;
            case 1:
                System.out.println("Mover Granjero");
                break;
            case 2:
                System.out.println("Mover Granjero y Zorro");
                break;
            case 3:
                System.out.println("Mover Granjero y Cabra");
                break;
            case 4:
                System.out.println("Mover Granjero y Repollo");
                break;
        }
    }    
        
    
    public static void main(String[] args) {
//PASO 1 Creamos las estructuras de datos    
        //Creamos un nodo Inicio
        Nodo NodIni = new Nodo();
        Nodo NodTem1 = new Nodo(3, 2);//Para ver
        Vector nSol;
        int nPosFil[] = new int[MAX_FILAS];
        int nPosCol[] = new int[MAX_COLUMNAS];
        int nSecFil, nSecCol, nFil, nCol, nPos ,nTem;
        
        int nCon = 0;
        Lista LisAbi = new Lista();
        Lista LisCer = new Lista();
        Lista LisGen = new Lista();
        
//PASO 2 Pasamos el primer nodo a Abiertos        
        LisAbi.insertar(NodIni);

        do{
//PASO 3 Si la lista abiertos está vacia salga indicando que no hay solución        
            if(LisAbi.pnIni == null){
                System.out.println("No hay solución");
                break;
            }        

//PASO 4 Pasar el primer nodo de abiertos a cerrados
            LisAbi.moverPrimerNodo(LisCer, 1);  //Usando política LIFO

//PASO 5 Si primer nodo de cerrados es el nodo meta salir buscando solución
            if((LisCer.pnIni.nNumRei==MAX_REINAS)||(nCon == MAX_CICLOS)){
                if(nCon == MAX_CICLOS){
                    System.out.println("Número de ciclos máximo");
                }else{
                    System.out.println("Hay solución");
                }                
                /*nSol = LisCer.pnIni.buscarSolucion();
                for(int nConTem=0; nConTem<nSol.size(); nConTem++){
                    interpreteSolucion((int)nSol.get(nConTem));
                }*/
                break;
            }

//PASO 6 Generar los hijos del primer nodo de cerrados
            //Generamos los nodos en orden aleatorio
            nPosFil = IntStream.rangeClosed(0, MAX_FILAS-1).toArray();
            Random nAle = new Random();
            for (int nConAle = nPosFil.length; nConAle > 0; nConAle--) {
                nPos = nAle.nextInt(nConAle);
                nTem = nPosFil[nConAle-1];
                nPosFil[nConAle - 1] = nPosFil[nPos];
                nPosFil[nPos] = nTem;
            }

            nPosCol = IntStream.rangeClosed(0, MAX_COLUMNAS-1).toArray();
            for (int nConAle = nPosCol.length; nConAle > 0; nConAle--) {
                nPos = nAle.nextInt(nConAle);
                nTem = nPosCol[nConAle-1];
                nPosCol[nConAle - 1] = nPosCol[nPos];
                nPosCol[nPos] = nTem;
            }        
            
            //Generamos los nodos en orden predeterminado
            /*for (int nConPre=0; nConPre<MAX_FILAS; nConPre++){
                nPosFil[nConPre]=nConPre;
            }           
            for (int nConPre=0; nConPre<MAX_COLUMNAS; nConPre++){
                nPosCol[nConPre]=nConPre;
            }*/

            //Generamos los nodos aplicando los operadores
            for(int nCon1=0; nCon1<MAX_FILAS; nCon1++){
                nFil = nPosFil[nCon1];
                for(int nCon2=0; nCon2<MAX_COLUMNAS; nCon2++){
                    nCol = nPosCol[nCon2];
                    LisGen.insertar(LisCer.pnIni.posicionReina(nFil, nCol));
                }
            }                       
            LisGen.eliminarNodoDeLista(LisCer);
            LisGen.eliminarNodoDeLista(LisAbi);
            
//PASO 7 Pasar la lista Generados a Abiertos
            LisAbi.unirLista(LisGen, 1);
            LisAbi.ordenarLista();                    

//PASO 8 Regresar al paso 3 
            //System.out.println("Final de la iteración      "+nCon);  //Para ver
            nCon++;
        }while(true); 
        //LisAbi.pnIni.showNodo();//Para ver    
        LisCer.pnIni.showNodo();//Para ver    
        System.out.println("Cantidad de nodos revisados:"+LisCer.longitudLista());
        System.out.println("Cantidad de nodos no revisados:"+LisAbi.longitudLista());
        System.out.println("Cantidad de nodos generados:"+(LisAbi.longitudLista()+LisCer.longitudLista()));
        


        //Probando la generación de una lista de números aleatorios

        for(int i=0; i<MAX_FILAS; i++){
            System.out.print(nPosFil[i]+" ");
        }/* */

        System.out.println(" ");

        for(int i=0; i<MAX_COLUMNAS; i++){
            System.out.print(nPosCol[i]+" ");
        }/* */

        
    }
    
}
