# GitHubPopular
  Aplicativo para desenvolvimento do teste aplicado pela [Concrete Solutions](https://bitbucket.org/suporte_concrete/desafio-android)
# Libs
  As bibliotecas utilizadas foram:
  * Glide
  * Retrofit
  * Robolectric
  * ConstraintLayout (criado ja utilizando a mesma)
# Arquitetura
  O aplicativo segue usando *MVP* com a seguinte estrutura de pacotes:
  
| Package | Descriçao |
| ------ | ------ |
| Contract | Contrato contendo interfaces para view e presenter |
| Entity   | Entidades (VO, DTO) utilizados no projeto |
| Model    | Model's utilizados pelo presenter |
| Presenter| Presenter utilizados para fazer ligaçao de view's com model's |
| UI       | Contem activity's e adapter|
| Utils    | Contem classes utilitarias para o projeto |
  
# Testes
  O projeto segue inicialmente somente utilizando teste unitarios com JUNIT para presenter e model 
  e testes utilizando robolectric para o contexto android
  
  Para executar os teste:
  ```sh
$ ./gradlew test
```
