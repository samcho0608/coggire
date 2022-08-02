# CoggiRe

---

CoggiRe is a backend for slack bot that supports Code Review and Merge Request on GitLab

# Abstract
## Naming : 
* Mix of Code Review + 끼리(Ggiri), which means together in Korean
* CoggiRe is a homophone of 코끼리, which is elephant in Korean

# Purpose
* to improve Code Review culture within a development team
* to automate following steps :
  - assigning merge request to a member of the repository
  - notifying the assignee of the merge request to review 

# Problems to Solve

- Manual assignment of merge request
- Manual check up of merge requests to review
- Manual creation of merge requests on GitLab
  - GitLab doesn’t provide any IDE plugin for merge request creation

# Docker 
## Build Arguments
* SLACK_ACCESS_TOKEN :  access token for the Slack API
## Containerize
* mount <PROJECT_ROOT>:/workspace/app