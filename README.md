[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/xbXrwRq8)
[![Open in Visual Studio Code](https://classroom.github.com/assets/open-in-vscode-2e0aaae1b6195c2367325f4f02e2d04e9abb55f0b24a779b69b11b9e10269abc.svg)](https://classroom.github.com/online_ide?assignment_repo_id=22817982&assignment_repo_type=AssignmentRepo)

# Oficina 1: Nivelamento Java - Gestão de Produtos

Este repositório contém a implementação da **Tarefa 1** da disciplina de Algoritmos e Estruturas de Dados II. O objetivo foi aplicar conceitos de Orientação a Objetos para gerenciar diferentes tipos de produtos em um sistema comercial.

## Funcionalidades Implementadas

### 1. Modelagem de Dados
- **Classe `Produto`**: Classe base com atributos protegidos (`protected`) para permitir o acesso controlado pelas subclasses.
- **Classe `ProdutoPerecivel`**: Implementa lógica de validade e descontos automáticos.
- **Classe `ProdutoNaoPerecivel`**: Implementa a herança simples para produtos sem data de expiração.

### 2. Regras de Negócio (Polimorfismo)
- **Cálculo de Preço**: Sobrescrita do método `valorDeVenda()` na classe perecível para aplicar **25% de desconto** caso o produto vença em 7 dias ou menos.
- **Segurança**: Implementação de travas que impedem a venda de produtos vencidos (lançamento de exceções).
- **Validação**: Verificação no construtor para impedir o cadastro de produtos com data de validade retroativa.

### 3. Qualidade e Testes
- **JUnit 5**: Inclusão de testes unitários para validar todos os cenários de preço (com e sem desconto) e as restrições de data.
- **Arquitetura**: Separação clara entre código-fonte (`src/`) e testes (`test/`).
  
---
**Desenvolvido por:** Gabriel Lacerda Lemos
**Curso:** Engenharia de Software - PUC Minas
