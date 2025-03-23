import subprocess
import os

# Define the directory to check for changes
code_dir = "../code/"

# Ensure the directory exists
if not os.path.exists(code_dir):
    raise FileNotFoundError(f"The directory {code_dir} does not exist.")

# Get the list of changed and untracked files
try:
    # Get tracked files with changes
    changed_files_output = subprocess.check_output(
        f"cd {code_dir} && git diff --name-only", shell=True, text=True
    )
    changed_files = changed_files_output.strip().split("\n")

    # Get untracked files (excluding files in .gitignore)
    untracked_files_output = subprocess.check_output(
        f"cd {code_dir} && git ls-files --others --exclude-standard", shell=True, text=True
    )
    print(untracked_files_output)
    untracked_files = untracked_files_output.strip().split("\n")
    print(untracked_files)

    # Combine tracked and untracked files
    all_files = list(filter(None, changed_files + untracked_files))  # Remove empty strings

    # Check if there are any changes or untracked files
    if not all_files:
        print("No changes or untracked files detected.")
    else:
        # Create a dictionary to store file names and their diffs or content
        file_diffs = {}

        # Process each file
        for file_name in all_files:
            # Skip the file models/test_model.ipynb
            if file_name.strip() == "models/test_model.ipynb":
                print(f"Skipping file: {file_name}")
                continue

            try:
                if file_name in changed_files:
                    # Get the diff for changed files
                    diff_output = subprocess.check_output(
                        f"cd {code_dir} && git diff {file_name}", shell=True, text=True
                    )
                    file_diffs[file_name] = f"Diff:\n{diff_output}"
                elif file_name in untracked_files:
                    # Get the content of untracked files
                    file_path = os.path.join(code_dir, file_name)
                    with open(file_path, "r", encoding="utf-8") as f:
                        file_content = f.read()
                    file_diffs[file_name] = f"Content:\n{file_content}"
            except Exception as e:
                print(f"Could not process file: {file_name}. Error: {e}")

        # Save the diffs and content to a file
        output_file = "changed_and_untracked_files.txt"
        with open(output_file, "w", encoding="utf-8") as f:
            for file_name, content in file_diffs.items():
                f.write(f"File: {file_name}\n")
                f.write(content)
                f.write("=" * 80 + "\n")  # Separator for readability

        print(f"Changed and untracked files have been saved to {output_file}.")

except subprocess.CalledProcessError as e:
    print(f"An error occurred while running git commands: {e}")