# Online News Monitoring and Analysis Platform

This project was developed by two students enrolled in the Computer Engineering Master’s program, specifically within the Software Platforms course at the University of Padova. In today’s world, characterized by an ever-growing influx of online content, the need for advanced tools to monitor and analyze public discussions on relevant topics has become increasingly important. This project aims to develop an innovative platform for monitoring and analyzing articles from online newspapers, utilizing a microservices architecture.

The platform is tailored to meet the needs of expert users, such as journalists and sociologists, who wish to explore and understand the themes discussed in the media regarding specific topics of interest. The project is divided into two main phases. The first phase involves monitoring and collecting articles related to a particular topic of interest, such as **Artificial Intelligence**, through a targeted search query. These articles are subsequently stored in a database.

The second phase focuses on analyzing the themes discussed within a subset of the collected articles. In this phase, users can specify an additional query, such as **ChatGPT**, to extract and analyze the primary themes covered in articles that match this query within the initial corpus. The platform then provides representations of these emerging themes, helping users to understand prevailing trends and discussions in the media.

Specifically, newspaper articles are retrieved from **The Guardian API**, stored in **MongoDB**, made searchable via **Elasticsearch**, and analyzed using **Mallet** for topic modeling. To ensure scalability and modularity, the platform is structured into several services, each running within Docker containers. Users interact with the platform through HTTP requests facilitated by a user-friendly UI, making the system accessible and efficient.

## Deployment Instructions

To facilitate reproducibility, a Docker Compose configuration has been created to orchestrate the deployment of the various microservices that make up the application. By containerizing each service, we can ensure that all dependencies, configurations, and environments are identical across different machines, making it easy for anyone to replicate the setup. The `docker-compose.yml` file defines how each service is built, configured, and interconnected.

To reproduce the environment and run the application, follow these steps:

1. **Clone the Repository**: Ensure you have a local copy of the project repository.

2. **Install Docker and Docker Compose**: Make sure Docker and Docker Compose are installed on your system. Docker Compose is essential for orchestrating the multi-container application.

3. **Run Docker Compose**: Navigate to the root directory of the project where the `docker-compose.yml` file is located and execute the following command:
   ```bash
   docker compose up --build
   ```
This command will build and start all the services defined in the `docker-compose.yml` file.

4. **Verify the Deployment**: Once the services are running, you can verify the deployment by accessing the frontend at `http://localhost:3000` and the API endpoints as documented.

5. **Data Persistence**: The data generated or modified during the execution of the services will be stored in the Docker volumes (`mongo-data` and `esdata`), ensuring that data is not lost when the containers are stopped.

By following the steps outlined above, anyone should be able to replicate the exact environment in which this application was developed and tested.
