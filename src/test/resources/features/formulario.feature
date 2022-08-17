@temp
Feature: temp
  

  @temp1
  Scenario: CT01- Preencher formulario
    Given que esteja na tela de formulario
    When preencher o formulario
    Then validar formulario preenchido
    