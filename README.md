# CompetitionSubmissionSystem
This tool is to ease hosting Coding/Data science competitions that only require an output as submission.
Example:
`Take a string as input and output the reverse of that string`
An input of "Daniel" should return "leinaD"

Instead of having to create and secure a web server, this app points to a folder, grabs submissions, compares them to a solution and returns a result.
The comparison is made line by line and returns a percentage from 0.000% to 100.000% of similarity between the submission and the solution expected.
The subfolders will be scanned every 3 seconds for new submissions

One possible configuration would be:
* Creating a Dropbox file system or any Network attached storage
* Mounting it to the host machine (the one that will run the CompetitionSubmissionSystem)
* Creating one subfolder per team or participant
* Giving participants write permissions in their subfolders 

See the app working here:
[![Competition submission system grabbing files](http://img.youtube.com/vi/_Zp71De6blw/0.jpg)](http://www.youtube.com/watch?v=_Zp71De6blw "Competition submission system grabbing files")


## Getting started
### Configuring the application
Download application.conf and change the file to match your configuration

Example:
`config {
   rootFolder = "C:/Users/DanielMateusPires/Dropbox/Competition"
   filesToMatch = ["^submission_.*", "^competition_.*"]
   solution = "C:/Users/DanielMateusPires/Documents/docs/solution.txt"
 }
 `
 
 This configuration would:
 * rootFolder: Choose `"C:/Users/DanielMateusPires/Dropbox/Competition"` as the root of the competition submission system, the submissions should then be added to in sub folders as such: `"C:/Users/DanielMateusPires/Dropbox/Competition/TeamA/submission_1.txt"` or `"C:/Users/DanielMateusPires/Dropbox/Competition/TeamB/competition_1.txt"`
 * filesToMatch: Defines an array of regex for submission file names to match (the system will look for files matching the regexes in the subfolders) 
 * solution: The solution for the competition (the submissions will be compared against this file)
 
 ### Building the app
 `sbt assembly`
 
 ### Launching the app
`java -Dconfig.file=path/to/application.conf -jar competition-submission-system.jar`

## Contributing
Clone the repo and work away!

### TODO
* Tests should be added to the project (I started it as a training project for Actors but it turned into a real tool :) )
* Storing the best submission for each team or participant (submission that scores better in the subfolder)
* Creating a WebUI to show the current status of the competition (leading board)
* Supporting multiple problems and submissions
