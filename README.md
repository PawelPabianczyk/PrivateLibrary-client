# PrivateLibrary-client
PrivateLibrary is a final application for a JAVA programming subject at my university. The goal was to create a desktop application that would allow the user to manage his own library. To use the application, the user must register and log in. The app allows you to add books, authors, publishers and genres. In addition, it provides the ability to view books added by other users.

<details open="open">
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
        <li><a href="#tools-used">Tools Used</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
  </ol>
</details>

## About The Project

![Main Page](/images/01.png)

PrivateLibrary-client is a frontend application made in Java (logic part) and JavaFX (visual part) technology. In addition, unit tests were performed in the JUnit 5 technology.

### Built With
* [Java](https://www.java.com/pl/)
* [JavaFX](https://openjfx.io/)
* [JUnit 5](https://junit.org/junit5/)

### Tools Used
* [IntelliJ IDEA](https://www.jetbrains.com/idea/)
* [Scene Builder](https://gluonhq.com/products/scene-builder/)

## Getting Started
Due to the fact that this is the frontend part, you must first download one of the available servers and run:
* [PrivateLibrary-server](https://github.com/PawelPabianczyk/PrivateLibrary-server) - database made in MySQL
* [PrivateLibrary-server-MongoDB](https://github.com/PawelPabianczyk/PrivateLibrary-server-MongoDB) - database made in MongoDB
	
Then clone the repository and run it in the selected Java-enabled IDE or using the console.

## Usage

![Main Page](/images/01.png)

Application start page. The user can log in or, if he does not have an account - register.<br/><br/>

![Main Page](/images/02.png)

Registration form - allows you to create a new account. If the user provides incorrect data, an error message will be displayed.<br/><br/>

![Main Page](/images/03.png)

Application home page. The user's basic data, which can be edited, and his favorite genre, author (information obtained from the database based on the user's library) are displayed.<br/><br/>

![Main Page](/images/04.png)

User data editing form.<br/><br/>

![Main Page](/images/05.png)

New book adding form. You can select basic information about the book, add a description, and choose an optional return date (if it is borrowed from someone). Similar forms can be found in the tabs for adding an author, genre or publisher.<br/><br/>

![Main Page](/images/06.png)

User book tab. Basic information is displayed here, which can be sorted properly, and also searched by author and genre, among others.<br/><br/>

![Main Page](/images/07.png)

Bookmark with all books of other application users. Their basic information is displayed here, e.g. who is the owner of a given book. In addition, they can be easily sorted and searched by author, genre or owner.<br/><br/>

## Contributing

Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

Distributed under the MIT License. See `LICENSE` for more information.

## Contact

Email - pawel.pabianczyk1999@gmail.com

LinkedIn - [Pawel Pabianczyk](https://www.linkedin.com/in/pawe%C5%82-pabia%C5%84czyk-a32693171/)

Project Link: [https://github.com/PawelPabianczyk/PrivateLibrary-client](https://github.com/PawelPabianczyk/PrivateLibrary-client)
