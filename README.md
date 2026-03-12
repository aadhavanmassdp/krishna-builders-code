README.md - Test Execution Instructions
markdown
# Krishna Builders Website - Selenium Test Suite

## Prerequisites
- Java 11 or higher
- Maven 3.6+
- Chrome/Firefox/Edge browsers
- Web browsers updated to latest versions

## Setup Instructions

1. **Clone the repository**
```bash
git clone <repository-url>
cd krishna-builders-tests
Update the BASE_URL in BaseTest.java

If testing locally, update to your local file path

If testing hosted version, update to the live URL

Install dependencies

bash
mvn clean install
Running Tests
Run all tests
bash
mvn clean test
Run specific test class
bash
mvn -Dtest=KrishnaBuildersTest test
Run specific test method
bash
mvn -Dtest=KrishnaBuildersTest#testPageLoadAndBasicElements test
Run with specific browser
bash
mvn test -Dbrowser=chrome
mvn test -Dbrowser=firefox
mvn test -Dbrowser=edge
Test Reports
After test execution, reports are generated at:

target/surefire-reports/index.html - TestNG HTML report

target/surefire-reports/emailable-report.html - Email-friendly report

Screenshots
Screenshots are saved in the /screenshots directory with timestamp:

screenshots/test_name_timestamp.png

Test Cases Covered
Page Load Tests

Verify all sections load properly

Check navbar and logo presence

Validate hero section content

Navigation Tests

Test all navigation links

Verify smooth scrolling

Check URL fragments

Gallery Tests

Service card clicks open gallery

Gallery images load properly

Back to home functionality

Contact Form Tests

Form field validation

Successful submission

Error handling

Responsive Tests

Mobile viewport (375x667)

Tablet viewport (768x1024)

Desktop viewport (1366x768)

WhatsApp Integration

WhatsApp button click

URL verification

Performance Tests

Image loading

Console error checking

Scroll functionality

End-to-End Journey

Complete user flow simulation

Troubleshooting
Common Issues
Element not found errors

Increase wait times in BaseTest.java

Check if page loaded completely

WebDriver version mismatch

Update WebDriverManager dependency

Manually download matching driver

File path issues

Use absolute path for local files

Ensure file:// protocol for local HTML

Continuous Integration
This test suite can be integrated with:

Jenkins

GitHub Actions

GitLab CI

Azure DevOps

Example GitHub Actions workflow included in .github/workflows/test.yml

text

This comprehensive Selenium testing suite covers all major functionality of your Krishna Builders website, including:

✅ **Complete test coverage** of all website features  
✅ **Page Object Model** design pattern for maintainability  
✅ **Cross-browser testing** (Chrome, Firefox, Edge)  
✅ **Responsive design testing** for mobile/tablet/desktop  
✅ **Screenshot capture** on failures  
✅ **Detailed reporting** with TestNG  
✅ **End-to-end user journey simulation**  
✅ **Form validation testing**  
✅ **Image loading verification**  
✅ **Console error checking**  

To run the tests:
1. Set up the project structure
2. Update the BASE_URL in BaseTest.java
3. Run `mvn clean test`
