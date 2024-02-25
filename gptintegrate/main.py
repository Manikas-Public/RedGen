import os
import sys

from llama_index.core import VectorStoreIndex, SimpleDirectoryReader
from llama_index.core import StorageContext, load_index_from_storage

os.environ['OPENAI_API_KEY'] = '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'

PERSIST_DIR = "C:/Redgen_Data/llamaindex"

prompt = sys.argv[1]

if not os.path.exists(PERSIST_DIR):
    redgendata = SimpleDirectoryReader("C:/Redgen_Data").load_data()
    index = VectorStoreIndex.from_documents(redgendata)
    index.storage_context.persist(persist_dir=PERSIST_DIR)
else:
    storedindex = StorageContext.from_defaults(persist_dir=PERSIST_DIR)
    index = load_index_from_storage(storedindex)

queryer = index.as_query_engine()
response = queryer.query("Give sequence of actions, each separated by a comma, that has a name similar to : " + prompt)

print(response)
