package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {
<<<<<<< HEAD
  LocalDate hoy = LocalDate.now();
  private double saldo;
  private List<Movimiento> movimientos = new ArrayList<>();
=======

  private double saldo = 0;
  private List<Movimiento> movimientos = new ArrayList<>();

  public Cuenta() {
    saldo = 0;
  }

>>>>>>> upstream/master
  public Cuenta(double montoInicial) {
    saldo = montoInicial;
  }

<<<<<<< HEAD
  private static final int MAX_DEPOSITOS_DIARIOS = 3;

  public void poner(double cuanto) {
    validarMonto(cuanto);
    validarLimiteDepositos(hoy);
    registrarDeposito(hoy, cuanto);
  }

  private void validarMonto(double cuanto) {
    if(cuanto <= 0) {
      throw new MontoNegativoException(cuanto + ": el monto a ingresar debe ser un valor positivo");
    }
  }

  private void validarLimiteDepositos(LocalDate fecha) {
    long depositosHoy = movimientos.stream()
        .filter(movimiento -> movimiento.fueDepositado(fecha))
        .count();

    if(depositosHoy >= MAX_DEPOSITOS_DIARIOS) {
      throw new MaximaCantidadDepositosException(
          "Ya excedio los " + MAX_DEPOSITOS_DIARIOS + " depositos diarios"
      );
    }
  }

  private void registrarDeposito(LocalDate fecha, double cuanto) {
    Movimiento.deposito(fecha, cuanto).agregateA(this);
  }

  public static Movimiento deposito(LocalDate fecha, double monto) {
    return new Movimiento(fecha, monto, true);
  }

  public void sacar(double cuanto){
    validarMonto(cuanto);
    validarExtraccion(cuanto);
    validarExtraccionDiaria(cuanto, hoy);
    registrarExtraccion(hoy, cuanto);
  }

  public void validarExtraccion(double cuanto){
    if(getSaldo() - cuanto < 0) {
      throw new SaldoMenorException("No puede sacar mas de " + getSaldo() + " $");
    }
  }

  public void validarExtraccionDiaria(double cuanto, LocalDate fecha){
    var montoExtraidoHoy = getMontoExtraidoA(fecha);
=======
  public void poner(double cuanto) {
    if (cuanto <= 0) {
      throw new MontoNegativoException(cuanto + ": el monto a ingresar debe ser un valor positivo");
    }

    if (getMovimientos().stream()
        .filter(movimiento -> movimiento.fueDepositado(LocalDate.now()))
        .count() >= 3) {
      throw new MaximaCantidadDepositosException("Ya excedio los " + 3 + " depositos diarios");
    }

    new Movimiento(LocalDate.now(), cuanto, true).agregateA(this);
  }

  public void sacar(double cuanto) {
    if (cuanto <= 0) {
      throw new MontoNegativoException(cuanto + ": el monto a ingresar debe ser un valor positivo");
    }
    if (getSaldo() - cuanto < 0) {
      throw new SaldoMenorException("No puede sacar mas de " + getSaldo() + " $");
    }
    var montoExtraidoHoy = getMontoExtraidoA(LocalDate.now());
>>>>>>> upstream/master
    var limite = 1000 - montoExtraidoHoy;
    if (cuanto > limite) {
      throw new MaximoExtraccionDiarioException(
          "No puede extraer mas de $ " + 1000 + " diarios, " + "límite: " + limite);
    }
<<<<<<< HEAD
  }

  public void registrarExtraccion(LocalDate fecha, double cuanto){
    Movimiento.extraccion(fecha, cuanto).agregateA(this);
  }

  public static Movimiento extraccion(LocalDate fecha, double cuanto) {
    return new Movimiento(LocalDate.now(), cuanto, false);
=======
    new Movimiento(LocalDate.now(), cuanto, false).agregateA(this);
>>>>>>> upstream/master
  }

  public void agregarMovimiento(LocalDate fecha, double cuanto, boolean esDeposito) {
    var movimiento = new Movimiento(fecha, cuanto, esDeposito);
    movimientos.add(movimiento);
  }

  public double getMontoExtraidoA(LocalDate fecha) {
    return getMovimientos().stream()
        .filter(movimiento -> !movimiento.isDeposito() && movimiento.getFecha().equals(fecha))
        .mapToDouble(Movimiento::getMonto)
        .sum();
  }

  public List<Movimiento> getMovimientos() {
    return movimientos;
  }

  public void setMovimientos(List<Movimiento> movimientos) {
    this.movimientos = movimientos;
  }

  public double getSaldo() {
    return saldo;
  }

  public void setSaldo(double saldo) {
    this.saldo = saldo;
  }

}
<<<<<<< HEAD

/*

Code smells identificados
1) Inicialización redundante
Con la variable saldo y el metodo Cuenta()

2) Duplicación de lógica entre constructores
public Cuenta() y public Cuenta(double montoInicial)

3) Long Method en poner(double cuanto)
El método valida monto, valida límite diario, crea movimiento,
agrega movimiento, tiene varias responsabilidades.

4) Código duplicado porque
se usa dos veces LocalDate.now()

5) Long Method en sacar(double cuanto)
El método valida monto, valida lo que se puede sacar,
y la cantidad que se puede extraer.

*/
=======
>>>>>>> upstream/master
