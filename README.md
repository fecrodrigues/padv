# PADV - Procura de Advogados

Este projeto tem como objetivo demonstrar os conhecimentos adquiridos na aula de Mobile através de um app que facilita a procura de profissionais da área jurídica a fim de solucionar algo caso o questão que necessita desse tipo de profissional.

## Estrutura

Como banco de dados foi utilizado o firabase;
Foi utilizada a seguinte estrutura de projeto aprendida em aula para melhor desacoplamento e organização de código:

- Data
  - remote/datasource
    - firebase - implementação da conversa do app com a camada de banco (no caso Firebase);
  - repository - Implementação dos métodos do repositório , no caso chamando o remote dataSource (Firestore);
- Domain
  - entity - Objetos utilizados para transitar as informações;
  - exception - Tratativas de erros customizados;
  - repository - Declaração das funções (interface) que o repositorio vai ter;
  - usecase - use cases com cada funcionalidade a ser usada pelas telas;
- Presentation
  - base e base/auth: Bases criadas como aprendido em aula para controle de telas logadas e não logadas;
  - ... Pacotes que contém o Fragment, ViewModel e ViewModelFactory (camada responsável pela logica da parte visual);

