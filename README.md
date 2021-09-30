
<!-- PROJECT SHIELDS -->
<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->

[![Contributors][contributors-shield]][contributors-url]
[![Contributors][contributions-shield]][contributors-url]
[![Contributors][size-shield]][contributors-url]

<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://github.com/SSeastCoders/account-service">
    <img src="core-library/src/main/resources/SimpleBankLogo.png" alt="Logo" width="100" height="100">
  </a>

<h3 align="center">Eastcoder Bank - Account Service</h3>

  <p align="center">
    All your banking needs are now taken care of with the Eastcoder Bank. This banking application implements functionalities such as member registration, executing
    credit card transactions, account balance management, member management, and loan
    management. Specifically the account service handles creation of accounts, removal of accounts, and edits to existing accounts.
    We promise not to steal any of your money, or at least not all.
  </p>
</p>

<!-- TABLE OF CONTENTS -->

## Table of Contents

  <details open="open">
  <summary></summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
    </li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#tools-and-plugins">Tools and Plugins</a></li>
    <li><a href="#the-full-project">Full Project Github</a></li>
    <li><a href="#the-eastcoder-team">The Eastcoder Team</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->

## About The Project
<details open="open">
<summary></summary>

[![Product Name Screen Shot][product-screenshot]]()

When accessing our app, you will be directed to a home page. Make an account to begin using our banking services.

</details>
<!-- GETTING STARTED -->

## Getting Started
<details open="open">
<summary></summary>
To get a local copy up and running follow these simple steps.
</details>

1. Clone the repo
   ```sh
   git clone https://github.com/SSeastCoders/account-service.git
   ```
2. Build the project
   ```sh
   mvn clean package
   ```
3. Run the jar file you have created
   ```sh
   java jar target/JARFILENAME.jar
   ```

## Contributing
<details open="open">
<summary></summary>

For those wishing to add to this service, please first clone the repository.
- `git clone https://github.com/SSeastCoders/account-service.git`

Then checkout the develop branch. This branch will be the most stable and current branch.
- `git switch develop`

You will also need to initialize the core-library submodule.
- `git submodule update --init --recursive`

Once on the develop branch, please create a new branch for whatever feature you are adding.
Please reference a Jira ticket number if relevant.
- `git checkout -b feature/Ticket#-YourFeatureName`

Importantly if you are going to make changes to the core library, also make a branch within the submodule of the service.
- `cd core-library`
- `git checkout -b feature/Ticket#-YourFeatureName`

Once you are satisfied with your changes, commit them to your feature branch.
- `git stage .`
- `git commit -m 'Explain your changes with a message'`

Then push your commit to a remote version of your branch.
- `git push origin feature/Ticket#-YourFeatureName`

Then you can open a pull request to have your changes merged into develop.
When opening a pull request, please request Alejandro Serna, Amanda Rolon, and Hazel Baker-Harvey as reviewers.

<!-- Used tools -->
</details>

## Tools and Plugins

- Maven
- Jenkins
- SonarQube
- Jacoco
- Docker
- CloudFormation
- For additional specific plugins, please refer to (`pom.xml`)

<!-- CONTACT -->

## The Full Project
To view the entire Eastcoder Bank application visit: [https://github.com/SSeastCoders](https://github.com/SSeastCoders)
<!-- ACKNOWLEDGEMENTS -->

## The Eastcoder Team

- [Alejandro Serna](https://github.com/asernass)
- [Amanda Rolon](https://github.com/arolonss)
- [Hazel Baker-Harvey](https://github.com/hazelbakerharvey)
- [Luis Fernandez](https://github.com/LuisF111)
- [Riley Milligan](https://github.com/Riley-Milligan)
<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->

[contributors-shield]: https://img.shields.io/github/contributors/SSeastCoders/account-service?style=for-the-badge
[contributors-url]: https://github.com/SSeastCoders/account-service/graphs/contributors
[contributions-shield]: https://img.shields.io/github/commit-activity/m/SSeastCoders/account-service?style=for-the-badge
[size-shield]: https://img.shields.io/github/languages/code-size/SSeastCoders/account-service?style=for-the-badge
[product-screenshot]: core-library/src/main/resources/HomePage.PNG