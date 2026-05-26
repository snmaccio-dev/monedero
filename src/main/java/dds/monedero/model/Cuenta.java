package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {
  LocalDate hoy = LocalDate.now();
  private double saldo;
  private List<Movimiento> movimientos = new ArrayList<>();
  public Cuenta(double montoInicial) {
    saldo = montoInicial;
  }

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
    var limite = 1000 - montoExtraidoHoy;
    if (cuanto > limite) {
      throw new MaximoExtraccionDiarioException(
          "No puede extraer mas de $ " + 1000 + " diarios, " + "límite: " + limite);
    }
  }

  public void registrarExtraccion(LocalDate fecha, double cuanto){
    Movimiento.extraccion(fecha, cuanto).agregateA(this);
  }

  public static Movimiento extraccion(LocalDate fecha, double cuanto) {
    return new Movimiento(LocalDate.now(), cuanto, false);
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
