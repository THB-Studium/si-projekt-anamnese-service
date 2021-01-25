#!/usr/bin/bash

# Add tags to an EC2 host or Image Profile
# Reboot and verify the result of $(env).

# Loads the Tags from the current instance
getInstanceTags () {
        # http://aws.amazon.com/code/1825 EC2 Instance Metadata Query Tool
        INSTANCE_ID=$(curl --silent http://169.254.169.254/latest/meta-data/instance-id)
        # Describe the tags of this instance
        aws ec2 describe-tags --region us-east-1 --filters "Name=resource-id,Values=$INSTANCE_ID"
}

# Convert the tags to environment variables.
# Based on https://github.com/berpj/ec2-tags-env/pull/1
tags_to_env () {
        tags=$1
        for key in $(echo $tags | /usr/bin/jq -r ".[][].Key"); do
                value=$(echo $tags | /usr/bin/jq -r ".[][] | select(.Key==\"$key\") | .Value")
                key=$(echo $key | /usr/bin/tr '-' '_' | /usr/bin/tr '[:lower:]' '[:upper:]')
                #echo "Exporting $key=$value"
                sed -i "/^export $key=\"/d" /home/ec2-user/.bash_profile
                echo "export $key=\"$value\"" >> /home/ec2-user/.bash_profile
        done
}
# Execute the commands
instanceTags=$(getInstanceTags)
echo $instanceTags
tags_to_env "$instanceTags"
