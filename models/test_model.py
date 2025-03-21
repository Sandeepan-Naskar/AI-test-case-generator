from openai import OpenAI
from pathlib import Path
import pandas as pd
import numpy as np

client = OpenAI(
    api_key = "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",
    base_url = "https://openrouter.ai/api/v1",
)

path1 = "./dataset/test.java"
path2 = "./dataset/detailed_transaction_history.json"

with open(path1, "r", encoding="utf-8") as f:
    dataset1 = f.read()

print(dataset1)

chat_completion = client.chat.completions.create(
    messages=[        
                {
                    "role": "user",
                    "content": "Read me the files in the variable " + dataset1 + " and describe the functionality of the code and write a test case in brief"
                }
            ],
    model="deepseek/deepseek-r1-distill-llama-70b:free",
    stream=False,
)

print(chat_completion)