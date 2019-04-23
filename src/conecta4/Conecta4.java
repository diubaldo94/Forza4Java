package conecta4;

import java.util.Scanner;

/**
 *
 * @author Isaac Soto Silvio Di Ubaldo
 */
public class Conecta4 {

    public int[][] tablero = new int[6][7];
    public int nroJugadas;
    public boolean ganado;
    private int sitioproxjug=-1;
    private int profmax = 9;
    
    public Conecta4() {
        nroJugadas = 0;
        ganado = false;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                tablero[i][j] = 0;
            }
        }
        mostrarTablero();
    }

    public void mostrarTablero() {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(tablero[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public int calcularPuntoMasbajo(int x) {
        if (tablero[5][x] == 0) {
            return 5;
        } else {
            for (int j = 0; j < 5; j++) {
                if (tablero[j + 1][x] != 0) {
                    return j;
                }
            }
        }
        return -1;
    }

    public boolean jugadaValida(int y, int x) {
        if (x >= 0 && x < 7 && y >= 0 && y < 6) {
            return (tablero[y][x] == 0);
        }
        return false;
    }

    public boolean realizarJugada(int jugador, int x) {
        if (x >= 0 && x < 7) {
            int y = calcularPuntoMasbajo(x);
            if (jugadaValida(y, x)) {
                tablero[y][x] = jugador;
                nroJugadas++;
                return true;
                //mostrarTablero();
            } else {
                System.out.println("Jugada Invalida, por favor ingrese una jugada valida");
                //mostrarTablero();
                return false;
            }
        } else {
            System.out.println("Jugada Invalida, por favor ingrese una jugada valida");
            mostrarTablero();
            return false;
        }
    }

    public void controlJuego() {
        if (nroJugadas >= 7) {
            int resultadoJuego = comprobarGanador();
            if (resultadoJuego == 1) {
                System.out.println("Jugador1 ha ganado!");
                ganado = true;
            } else if (resultadoJuego == 2) {
                System.out.println("Jugador2 ha ganado!");
                ganado = true;
            } else if (resultadoJuego == 0) {
                System.out.println("Se ha producido un empate");
                ganado = true;
            }
        }
    }

    public int comprobarGanador() {
        int contador1 = 0, contador2 = 0;
        for (int i = 5; i >= 0; --i) {
            for (int j = 0; j <= 6; ++j) {
                if (tablero[i][j] == 0) {
                    continue;
                }

                //Chequear elementos por la derecha
                if (j <= 3) {
                    for (int k = 0; k < 4; ++k) {
                        if (tablero[i][j + k] == 1) {
                            contador1++;
                        } else if (tablero[i][j + k] == 2) {
                            contador2++;
                        } else {
                            break;
                        }
                    }
                    if (contador1 == 4) {
                        return 1;
                    } else if (contador2 == 4) {
                        return 2;
                    }
                    contador1 = 0;
                    contador2 = 0;
                }

                //Chequear elemntos por arriba
                if (i >= 3) {
                    for (int k = 0; k < 4; ++k) {
                        if (tablero[i - k][j] == 1) {
                            contador1++;
                        } else if (tablero[i - k][j] == 2) {
                            contador2++;
                        } else {
                            break;
                        }
                    }
                    if (contador1 == 4) {
                        return 1;
                    } else if (contador2 == 4) {
                        return 2;
                    }
                    contador1 = 0;
                    contador2 = 0;
                }

                //Chequear en diagonal por arriba-derecha
                if (j <= 3 && i >= 3) {
                    for (int k = 0; k < 4; ++k) {
                        if (tablero[i - k][j + k] == 1) {
                            contador1++;
                        } else if (tablero[i - k][j + k] == 2) {
                            contador2++;
                        } else {
                            break;
                        }
                    }
                    if (contador1 == 4) {
                        return 1;
                    } else if (contador2 == 4) {
                        return 2;
                    }
                    contador1 = 0;
                    contador2 = 0;
                }

                //Chequear en diagonal por arriba-izquierda
                if (j >= 3 && i >= 3) {
                    for (int k = 0; k < 4; ++k) {
                        if (tablero[i - k][j - k] == 1) {
                            contador1++;
                        } else if (tablero[i - k][j - k] == 2) {
                            contador2++;
                        } else {
                            break;
                        }
                    }
                    if (contador1 == 4) {
                        return 1;
                    } else if (contador2 == 4) {
                        return 2;
                    }
                    contador1 = 0;
                    contador2 = 0;
                }
            }
        }

        for (int j = 0; j < 7; ++j) {
            //el juego aùn no acabò
            if (tablero[0][j] == 0) {
                return -1;
            }
        }
        //empate!
        return 0;
    }

    public boolean terminado() {
        return ganado;
    }
    
    public int jugadaSistema(){
        sitioproxjug = -1;
	minimax(0, 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
	return sitioproxjug;
    }
    
    public int minimax(int profundidad, int turno, int alfa, int beta){
	        
	        if(beta<=alfa){if(turno == 1) return Integer.MAX_VALUE; else return Integer.MIN_VALUE; }
	        int resjuego = comprobarGanador();
	        
	        if(resjuego==1)return Integer.MAX_VALUE/2;
	        else if(resjuego==2)return Integer.MIN_VALUE/2;
	        else if(resjuego==0)return 0; 
	        
	        if(profundidad==profmax)return evaluarTabla();
	        
	        int ventajaMax=Integer.MIN_VALUE, ventajaMin = Integer.MAX_VALUE;
	                
	        for(int j=0;j<=6;++j){
	            
	            int ventajaActual = 0;
	            
	            if(!jugadaValida(calcularPuntoMasbajo(j),j)) continue; 
	            
	            if(turno==1){
	                    realizarJugada(1, j);
	                    ventajaActual = minimax(profundidad+1, 2, alfa, beta);
	                    
	                    if(profundidad==0){
	                        if(ventajaActual > ventajaMax) sitioproxjug = j; 
	                        if(ventajaActual == Integer.MAX_VALUE/2){anularJugada(j);break;}
	                    }
	                    
	                    ventajaMax = Math.max(ventajaActual, ventajaMax);
	                    
	                    alfa = Math.max(ventajaActual, alfa);  
	            } 
	            else if(turno==2){
	                    realizarJugada(2, j);
	                    ventajaActual = minimax(profundidad+1, 1, alfa, beta);
	                    ventajaMin = Math.min(ventajaActual, ventajaMin);
	                    
	                    beta = Math.min(ventajaActual, beta); 
	            }  
	            anularJugada(j); 
	            if(ventajaActual == Integer.MAX_VALUE || ventajaActual == Integer.MIN_VALUE) break; 
	        }  
	        return turno==1?ventajaMax:ventajaMin;
    }
    
    public void anularJugada(int columna){
        for(int i=0;i<=5;++i){
            if(tablero[i][columna] != 0) {
                tablero[i][columna] = 0;
                break;
            }
        }        
    }
    
    public int evaluarTabla(){
	      
	        int contSistema=1;
	        int cuenta=0;
	        int vacios = 0;
	        int k=0, masjugadas=0;
	        for(int i=5;i>=0;--i){
	            for(int j=0;j<=6;++j){
	                
	                if(tablero[i][j]==0 || tablero[i][j]==2) continue; 
	                
	                if(j<=3){ 
	                    for(k=1;k<4;++k){
	                        if(tablero[i][j+k]==1)contSistema++;
	                        else if(tablero[i][j+k]==2){contSistema=0;vacios = 0;break;}
	                        else vacios++;
	                    }
	                     
	                    masjugadas = 0; 
	                    if(vacios>0) 
	                        for(int c=1;c<4;++c){
	                            int columna = j+c;
	                            for(int m=i; m<= 5;m++){
	                             if(tablero[m][columna]==0)masjugadas++;
	                                else break;
	                            } 
	                        } 
	                    
	                    if(masjugadas!=0) cuenta += calculoVentaja(contSistema, masjugadas);
	                    contSistema=1;   
	                    vacios = 0;
	                } 
	                
	                if(i>=3){
	                    for(k=1;k<4;++k){
	                        if(tablero[i-k][j]==1)contSistema++;
	                        else if(tablero[i-k][j]==2){contSistema=0;break;} 
	                    } 
	                    masjugadas = 0; 
	                    
	                    if(contSistema>0){
	                        int columna = j;
	                        for(int m=i-k+1; m<=i-1;m++){
	                         if(tablero[m][columna]==0)masjugadas++;
	                            else break;
	                        }  
	                    }
	                    if(masjugadas!=0) cuenta += calculoVentaja(contSistema, masjugadas);
	                    contSistema=1;  
	                    vacios = 0;
	                }
	                 
	                if(j>=3){
	                    for(k=1;k<4;++k){
	                        if(tablero[i][j-k]==1)contSistema++;
	                        else if(tablero[i][j-k]==2){contSistema=0; vacios=0;break;}
	                        else vacios++;
	                    }
	                    masjugadas=0;
	                    if(vacios>0) 
	                        for(int c=1;c<4;++c){
	                            int columna = j- c;
	                            for(int m=i; m<= 5;m++){
	                             if(tablero[m][columna]==0)masjugadas++;
	                                else break;
	                            } 
	                        } 
	                    
	                    if(masjugadas!=0) cuenta += calculoVentaja(contSistema, masjugadas);
	                    contSistema=1; 
	                    vacios = 0;
	                }
	                 
	                if(j<=3 && i>=3){
	                    for(k=1;k<4;++k){
	                        if(tablero[i-k][j+k]==1)contSistema++;
	                        else if(tablero[i-k][j+k]==2){contSistema=0;vacios=0;break;}
	                        else vacios++;                        
	                    }
	                    masjugadas=0;
	                    if(vacios>0){
	                        for(int c=1;c<4;++c){
	                            int columna = j+c, fila = i-c;
	                            for(int m=fila;m<=5;++m){
	                                if(tablero[m][columna]==0)masjugadas++;
	                                else if(tablero[m][columna]==1);
	                                else break;
	                            }
	                        } 
	                        if(masjugadas!=0) cuenta += calculoVentaja(contSistema, masjugadas);
	                        contSistema=1;
	                        vacios = 0;
	                    }
	                }
	                 
	                if(i>=3 && j>=3){
	                    for(k=1;k<4;++k){
	                        if(tablero[i-k][j-k]==1)contSistema++;
	                        else if(tablero[i-k][j-k]==2){contSistema=0;vacios=0;break;}
	                        else vacios++;                        
	                    }
	                    masjugadas=0;
	                    if(vacios>0){
	                        for(int c=1;c<4;++c){
	                            int columna = j-c, fila = i-c;
	                            for(int m=fila;m<=5;++m){
	                                if(tablero[m][columna]==0)masjugadas++;
	                                else if(tablero[m][columna]==1);
	                                else break;
	                            }
	                        } 
	                        if(masjugadas!=0) cuenta += calculoVentaja(contSistema, masjugadas);
	                        contSistema=1;
	                        vacios = 0;
	                    }
	                } 
	            }
	        }
	        return cuenta;
    }
    
    int calculoVentaja(int cuentaIA, int masjugadas){   
	int cuentajug = 4 - masjugadas;
	if(cuentaIA==0)return 0;
	else if(cuentaIA==1)return 1*cuentajug;
	else if(cuentaIA==2)return 10*cuentajug;
	else if(cuentaIA==3)return 100*cuentajug;
	else return 1000;
    }
    public static void main(String[] args) {
        Conecta4 juego = new Conecta4();
        Scanner sc = new Scanner(System.in);
        System.out.println("Para ingresar una jugada teclea un numero entre 1 y 7");
        System.out.println("Deseas jugar primero? s/n");
        if(sc.next().equals("s"))
            juego.realizarJugada(2, sc.nextInt()-1);
        juego.realizarJugada(1, 3);
        juego.mostrarTablero();
        
        while (true) {
            juego.controlJuego();
            if (!juego.terminado()) {
                if(juego.realizarJugada(2, sc.nextInt()-1)){
                    juego.controlJuego();
                    if(juego.terminado()){
                        juego.mostrarTablero();
                        break;
                    }
                } else {
                    juego.realizarJugada(2, sc.nextInt()-1);
                }

                juego.mostrarTablero();
                juego.realizarJugada(1, juego.jugadaSistema());
                juego.controlJuego();
                if(juego.terminado()){
                    juego.mostrarTablero();
                    break;
                }
                juego.mostrarTablero();
            } else {
                break;
            }

        }
    }

}
