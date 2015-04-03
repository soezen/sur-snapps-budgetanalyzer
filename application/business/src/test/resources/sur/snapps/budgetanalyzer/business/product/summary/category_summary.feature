Feature: CategorySummary

  Scenario: Add one ProductType with one Category
    Given existing summary
      """
      {"id": "SUMMARY", "name": "Categories for {month}", "children": []}
      """
    When adding ProductType PT-1 with categories: B
    Then expected summary
      """
      {"id": "SUMMARY", "name": "Categories for {month}", "children": [
          {"id": "B", "name": "B", "children": [
              {"id": "PT-1", "name": "PT-1", "size": 10}]}]}
      """

  Scenario: Add two ProductTypes with same Category
    Given existing summary
      """
      {"id": "SUMMARY", "name": "Categories for {month}", "children": []}
      """
    When adding ProductType PT-1 with categories: A
    When adding ProductType PT-2 with categories: A
    Then expected summary
      """
      {"id": "SUMMARY", "name": "Categories for {month}", "children": [
        {"id": "A", "name": "A", "children": [
            {"id": "PT-1", "name": "PT-1", "size": 10},
            {"id": "PT-2", "name": "PT-2", "size": 10}]}]}
      """

  Scenario: Add two ProductTypes with different Category
    Given existing summary
      """
      {"id": "SUMMARY", "name": "Categories for {month}", "children": []}
      """
    When adding ProductType PT-1 with categories: A
    When adding ProductType PT-2 with categories: B
    Then expected summary
      """
      {"id": "SUMMARY", "name": "Categories for {month}", "children": [
        {"id": "A", "name": "A", "children": [
          {"id": "PT-1", "name": "PT-1", "size": 10}]},
        {"id": "B", "name": "B", "children": [
          {"id": "PT-2", "name": "PT-2", "size": 10}]}]}
      """

  Scenario: Add one ProductType with multiple Categories
    Given existing summary
      """
      {"id": "SUMMARY", "name": "Categories for {month}", "children": []}
      """
    When adding ProductType PT-1 with categories: A, B, C
    Then expected summary
      """
      {"id": "SUMMARY", "name": "Categories for {month}", "children": [
        {"id": "A", "name": "A", "children": [
          {"id": "B", "name": "B", "children": [
            {"id": "C", "name": "C", "children": [
              {"id": "PT-1", "name": "PT-1", "size": 10}]}]}]}]}
      """

  Scenario: Add two ProductTypes with multiple Categories
    Given existing summary
    """
      {"id": "SUMMARY", "name": "Categories for {month}", "children": []}
      """
    When adding ProductType PT-1 with categories: A, B, C
    When adding ProductType PT-2 with categories: A, D
    Then expected summary
    """
      {"id": "SUMMARY", "name": "Categories for {month}", "children": [
        {"id": "A", "name": "A", "children": [
          {"id": "B", "name": "B", "children": [
            {"id": "C", "name": "C", "children": [
              {"id": "PT-1", "name": "PT-1", "size": 10}]}]},
          {"id": "D", "name": "D", "children": [
            {"id": "PT-2", "name": "PT-2", "size": 10}]}]}]}
      """

  Scenario: Add several ProductTypes with multiple Categories
    Given existing summary
      """
      {"id": "SUMMARY", "name": "Categories for {month}", "children": []}
      """
    When adding ProductType PT-1 with categories: A, B
    When adding ProductType PT-2 with categories: A, D
    When adding ProductType PT-3 with categories: A, B
    When adding ProductType PT-4 with categories: A, B
    Then expected summary
      """
      {"id": "SUMMARY", "name": "Categories for {month}", "children": [
        {"id": "A", "name": "A", "children": [
          {"id": "B", "name": "B", "children": [
            {"id": "PT-1", "name": "PT-1", "size": 10},
            {"id": "PT-3", "name": "PT-3", "size": 10},
            {"id": "PT-4", "name": "PT-4", "size": 10}]},
          {"id": "D", "name": "D", "children": [
            {"id": "PT-2", "name": "PT-2", "size": 10}]}]}]}
      """


  Scenario: Add several ProductTypes with multiple Categories
    Given existing summary
      """
      {"id": "SUMMARY", "name": "Categories for {month}", "children": []}
      """
    When adding ProductType PT-1 with categories: A, B
    When adding ProductType PT-2 with categories: A, D
    When adding ProductType PT-3 with categories: A, D
    When adding ProductType PT-4 with categories: A, D
    Then expected summary
      """
      {"id": "SUMMARY", "name": "Categories for {month}", "children": [
        {"id": "A", "name": "A", "children": [
          {"id": "B", "name": "B", "children": [
            {"id": "PT-1", "name": "PT-1", "size": 10}]},
          {"id": "D", "name": "D", "children": [
            {"id": "PT-2", "name": "PT-2", "size": 10},
            {"id": "PT-3", "name": "PT-3", "size": 10},
            {"id": "PT-4", "name": "PT-4", "size": 10}]}]}]}
      """