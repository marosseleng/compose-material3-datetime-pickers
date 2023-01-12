#!/bin/bash
#
#    Copyright 2023 Maroš Šeleng
#
#    Licensed under the Apache License, Version 2.0 (the "License");
#    you may not use this file except in compliance with the License.
#    You may obtain a copy of the License at
#
#        http://www.apache.org/licenses/LICENSE-2.0
#
#    Unless required by applicable law or agreed to in writing, software
#    distributed under the License is distributed on an "AS IS" BASIS,
#    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#    See the License for the specific language governing permissions and
#    limitations under the License.
#

if [[ -z ${GITHUB_TOKEN} ]]; then
  echo "Missing GITHUB_TOKEN variable"
  exit 1
fi

if [[ -z ${GITHUB_REPOSITORY} ]]; then
  echo "Missing GITHUB_REPOSITORY variable"
  exit 1
fi

if [[ -z ${PR_BRANCH} ]]; then
  echo "Missing PR_BRANCH variable"
  exit 1
fi

./gradlew recordPaparazziDebug

PR_NUMBER=${GITHUB_REF#refs/pull/}
PR_NUMBER=${PR_NUMBER/\/merge/}
NEW_BRANCH_NAME="snapshots/pr-$PR_NUMBER"
echo "::set-output name=PR_NUMBER::$PR_NUMBER"

git config user.name "CI/CD"
git config user.email "maros@marosseleng.com"
git fetch --all
git checkout --track "origin/$PR_BRANCH"
git checkout -b "$NEW_BRANCH_NAME"
git add -A
git commit -m "Update screenshots"
git push --force "https://$GITHUB_TOKEN@github.com/$GITHUB_REPOSITORY.git"
echo "::set-output name=PR_COMMENT::\"Screenshot tests failed.\\n\\n[See differences](https://github.com/$GITHUB_REPOSITORY/compare/$PR_BRANCH...$NEW_BRANCH_NAME)\\n\\nMerge the branch if it's an intentional change.\""