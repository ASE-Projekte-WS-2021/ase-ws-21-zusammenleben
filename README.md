<p align="left">
 <img src="https://github.com/ASE-Projekte-WS-2021/ase-ws-21-zusammenleben/blob/main/imagesforwiki/icon_WGFinance.png" style="border-radius: 12px; width: 10%;"/><br />
</p>

# WGFinance: Better together

Moving into a new flat share is usually very stressful. One roommate leaves and a new one joins. It is not uncommon that furniture that was paid for out of the flat-sharing fund is taken over by the new flatmate and payments have to be made. Usually, a sum is simply roughly estimated and an amount is demanded for which no one seems to have a receipt. Often, however, it is not just a piece of furniture, but a whole series of things that were paid for together, but are now to remain in the shared apartment, but one person wants to be compensated. Especially in shared flats, where there is a constant change of flatmates, this creates financial imbalances. Incorrectly noted bills of more than one year, borrowed money or a purchase participation which was forgotten to dissolve, are problems which come quite in most WGs on the flatmates/inside. The app WGFinance solves this problem with a holistic, fully digital approach that can be integrated into the daily life of the flat-sharing community.

Users of the WGFinance application can easily create new WGs or be invited via a link. Users can create a list for a common purchase. After the payments that have been made have been entered into the application and divided among any number of flatmates, they are displayed in an overview.
Through the simple overview, payments made are offset against each other. The payments made are kept in an archived overview. And if the apartment community should dissolve, then the resignation is just a simple click away.

<p align="center">
 <img src="https://drive.google.com/file/d/14AeWrzOE9uUVjFh23pTXzoZPCwBCdvhE/view?usp=sharing" style="border-radius: 12px; width: 80%;"/><br />
</p>


<br/>

## Development Setup

1. Make sure, that Java (>= version 11) is installed and set up correctly (check with javac -version command)
  - If another version was installed previously and the environment variable is corrected, you may need to restart
  - https://www.oracle.com/java/technologies/downloads/#jdk17-windows
2. Download the Git-Hub repository - or the existing APK - and run the application.

<br/>

##Git Hooks

On every commit git automatically runs checkstyle before committing.

<br/>

## CI/CD Pipeline

On every push and merge the pipeline runs checkstyle, all tests and checks if the code builds.
If a version tag (e.g. v1) was added, it will be released on the github project.

<br/>

## Branching

- No pushes direct to main if not approved
- Split into two groups working on two branches (Interface for UI, Development for MVP). These are regularly merged on backup.
- Quality gates, that need to be succeeded before a merge to backup is possible:
 - Reviewed by 1 peer within the group
 - Succeeded the build pipeline (includes style check, tests and possibly other gates)
 - Test of the merge from at least two peers.

<br/>

## Development Workflow

**Regularly check for PRs that need to be reviewed.**

1. Communication on who does what with weekly meeting to talk about progress, issues and extra meeting to present the progress
2. Issues were closed by someone taking care of them. The takeover was based on who was already working on this sub-area.
3. Work on your branch
4. Progress was merged after everyone was done to the backup branch.
5. Merge conflicts were resolved.

<br/>

## Contributors

The individual team members did not have fixed areas of responsibility. Everybody worked in many areas, yet thematic focuses have emerged over time.
To learn more about the division of tasks and the schedule follow this link to our Google Drive Doc: https://tinyurl.com/y25f6kdn 
