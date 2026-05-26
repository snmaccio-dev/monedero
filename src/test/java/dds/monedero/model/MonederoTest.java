package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class MonederoTest {
  private Cuenta cuenta;

  @BeforeEach
  void init() {
    cuenta = new Cuenta();
  }

  @Test
  @DisplayName("Es posible poner $1500 en una cuenta vacía")
  void Poner() {
<<<<<<< HEAD

    cuenta.poner(1500);
    assertEquals(1500, cuenta.getSaldo(), 0.01);

=======
    cuenta.poner(1500);
>>>>>>> upstream/master
  }

  @Test
  @DisplayName("No es posible poner montos negativos")
  void PonerMontoNegativo() {
    assertThrows(MontoNegativoException.class, () -> cuenta.poner(-1500));
  }

  @Test
  @DisplayName("Es posible realizar múltiples depósitos consecutivos")
  void TresDepositos() {
    cuenta.poner(1500);
    cuenta.poner(456);
    cuenta.poner(1900);
<<<<<<< HEAD
    assertEquals(3856, cuenta.getSaldo(), 0.01);
  }

  @Test
  @DisplayName("No se puede superar el límite de 3 depósitos diarios")
  void maxDepositos() {
    cuenta.poner(100);
    cuenta.poner(100);
    cuenta.poner(100);
    assertThrows(MaximaCantidadDepositosException.class,
        () -> cuenta.poner(100));
  }

  @Test
  @DisplayName("No se permite depositar monto negativo")
  void depositoNegativo() {
    assertThrows(MontoNegativoException.class,
        () -> cuenta.poner(-1500));
  }

  @Test
  @DisplayName("No se puede extraer más que el saldo")
  void extraerMasQueSaldo() {
    cuenta.poner(500);
    assertThrows(SaldoMenorException.class,
        () -> cuenta.sacar(1000));
  }

  @Test
  @DisplayName("No se puede superar el límite diario de extracción")
  void limiteExtraccionDiario() {
    cuenta.poner(5000);

    assertThrows(MaximoExtraccionDiarioException.class,
        () -> cuenta.sacar(2000));
  }

  @Test
  @DisplayName("No se permite extraer monto negativo")
  void extraccionNegativa() {
    assertThrows(MontoNegativoException.class,
        () -> cuenta.sacar(-500));
  }
=======
  }

  @Test
  @DisplayName("No es posible superar la máxima cantidad de depositos diarios")
  void MasDeTresDepositos() {
    assertThrows(MaximaCantidadDepositosException.class, () -> {
      cuenta.poner(1500);
      cuenta.poner(456);
      cuenta.poner(1900);
      cuenta.poner(245);
    });
  }

  @Test
  @DisplayName("No es posible extraer más que el saldo disponible")
  void ExtraerMasQueElSaldo() {
    assertThrows(SaldoMenorException.class, () -> {
      cuenta.setSaldo(90);
      cuenta.sacar(1001);
    });
  }

  @Test
  @DisplayName("No es posible extraer más que el límite diario")
  void ExtraerMasDe1000() {
    assertThrows(MaximoExtraccionDiarioException.class, () -> {
      cuenta.setSaldo(5000);
      cuenta.sacar(1001);
    });
  }

  @Test
  @DisplayName("No es posible extraer un monto negativo")
  void ExtraerMontoNegativo() {
    assertThrows(MontoNegativoException.class, () -> cuenta.sacar(-500));
  }

}
>>>>>>> upstream/master
