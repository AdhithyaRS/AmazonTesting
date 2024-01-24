@startuml
left to right direction

package "Amazon" {
    [pom.xml]
    package "src/tests" {
        [BaseTest]
        package "database" {
            [DatabaseBaseTest] <|-- [CRUDTest]
        }
        package "functional" {
            [BaseTest] <|-- [FunctionalBaseTest]
            [FunctionalBaseTest] <|-- [SignUpTest]
            [FunctionalBaseTest] <|-- [SignInTest]
            [FunctionalBaseTest] <|-- [SearchProductTest]
        }
        package "integration" {
            [BaseTest] <|-- [IntegrationBaseTest]
            [IntegrationBaseTest] <|-- [LoginToBuyIntegrationTest]
        }
    }
    package "src/main" {
        package "pageObjectModel" {
            [PageOutline] <|-- [BasePage]
            [BasePage] <|-- [Products]
            [BasePage] <|-- [SignInPage]
            [BasePage] <|-- [SignUpPage]
        }
    }

    package "resources" {
        package "TestNG.xml files" {
            [CombinedSuites.xml]
            [MyFIUSuite.xml]
            [MyDatabaseSuite.xml]
        }
        package "Test data files" {
            [LoginToBuyIntegrationFeed.csv]
            [ProductSearchFeed.csv]
            [SignInFeed.csv]
            [SignUpFeed.csv]
            [customer_data.csv]
            [order_data.csv]
            [order_details_data.csv]
            [product_data.csv]
            [sales_data.csv]
        }
    }

    package "listeners" {
        [AllureListener] 
        [AllureListenerDB]
    }
    
    package "customInterfaces" {
        [ParameterLabel]
    }

    
    
    [CombinedSuites.xml] - [MyFIUSuite.xml]
    [CombinedSuites.xml] - [MyDatabaseSuite.xml]
    [pom.xml] - [CombinedSuites.xml]
    
    [ParameterLabel] - [SignUpTest]
    [ParameterLabel] - [SignInTest]
    [ParameterLabel] - [SearchProductTest]
    [ParameterLabel] - [LoginToBuyIntegrationTest]
    [MyFIUSuite.xml] - [SignUpTest]
    [MyFIUSuite.xml] - [SignInTest]
    [MyFIUSuite.xml] - [SearchProductTest]
    [MyFIUSuite.xml] - [LoginToBuyIntegrationTest]
    [MyDatabaseSuite.xml] - [CRUDTest]
    [ParameterLabel] - [AllureListener]
    [AllureListener] - [MyFIUSuite.xml]
    [AllureListenerDB] - [MyDatabaseSuite.xml]
    [database] - [Test data files]
    [functional] - [Test data files]
    [integration] - [Test data files]
    [pageObjectModel] - [functional]
    [pageObjectModel] - [integration]
}
@enduml
